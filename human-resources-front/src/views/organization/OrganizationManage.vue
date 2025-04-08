<template>
  <div class="organization-manage">
    <!-- 公司选择器 -->
    <div class="company-selector">
      <el-select v-model="selectedCompanyId" placeholder="请选择公司" @change="handleCompanyChange">
        <el-option
          v-for="company in companyList"
          :key="company.id"
          :label="company.name"
          :value="company.id"
        />
      </el-select>
    </div>
    <!-- 公司信息展示区域 -->
    <div class="company-info" v-if="currentCompany">
      <h2>{{ currentCompany.name }}</h2>
      <p>联系人：{{ currentCompany.contactPerson }} | 电话：{{ currentCompany.contactPhone }}</p>
    </div>
    <div class="operation-bar">
      <el-button type="primary" @click="handleAdd">新增组织</el-button>
    </div>
    <el-table
      :data="organizationList"
      row-key="id"
      border
      default-expand-all
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column prop="name" label="组织名称" width="180" />
      <el-table-column prop="level" label="组织层级" width="100" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-switch
            v-model="row.status"
            :active-value="1"
            :inactive-value="0"
            @change="handleStatusChange(row)"
          />
        </template>
      </el-table-column>
      <el-table-column label="操作" width="200">
        <template #default="{ row }">
          <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
          <el-button type="danger" link @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 新增/编辑对话框 -->
    <el-dialog
      :title="dialogTitle"
      v-model="dialogVisible"
      width="500px"
      @close="resetForm"
    >
      <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="所属公司" prop="companyId">
          <el-input
            v-model="currentCompany.name"
            disabled
            placeholder="自动关联当前公司"
          />
        </el-form-item>
        <el-form-item label="组织名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入组织名称" />
        </el-form-item>
        <el-form-item label="上级组织" prop="parentId">
          <el-select
            v-model="form.parentId"
            placeholder="请选择上级组织"
            clearable
          >
            <el-option
              v-for="org in flatOrganizationList"
              :key="org.id"
              :label="org.name"
              :value="org.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="组织层级" prop="level">
          <el-input-number v-model="form.level" :min="1" :max="10" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import axios from 'axios'

const organizationList = ref([])
const flatOrganizationList = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref(null)
const currentCompany = ref(null)
const companyList = ref([])
const selectedCompanyId = ref(null)

// 将树形数据转换为扁平数组
const flattenOrganizations = (organizations) => {
  const result = []
  const flatten = (items) => {
    items.forEach(item => {
      result.push({
        id: item.id,
        name: item.name
      })
      if (item.children && item.children.length > 0) {
        flatten(item.children)
      }
    })
  }
  flatten(organizations)
  return result
}

const form = ref({
  id: null,
  name: '',
  parentId: null,
  level: 1,
  status: 1,
  companyId: null
})

const rules = {
  name: [{ required: true, message: '请输入组织名称', trigger: 'blur' }],
  level: [{ required: true, message: '请选择组织层级', trigger: 'change' }]
}

// 获取公司列表数据
const getCompanyList = async () => {
  try {
    const res = await axios.get('http://localhost:8080/api/company/list')
    companyList.value = res.data.data.records
    console.log(companyList.value)
    if (companyList.value.length > 0 && !selectedCompanyId.value) {
      selectedCompanyId.value = companyList.value[0].id
      await handleCompanyChange(selectedCompanyId.value)
    }
  } catch (error) {
    console.error('获取公司列表失败：', error)
    ElMessage.error('获取公司列表失败')
  }
}

// 处理公司切换
const handleCompanyChange = async (companyId) => {
  try {
    // 获取选中公司的详细信息
    const companyRes = await axios.get(`http://localhost:8080/api/company/${companyId}`)
    currentCompany.value = companyRes.data.data
    form.value.companyId = companyId

    // 获取该公司的组织树形数据
    const res = await axios.get(`http://localhost:8080/api/organization/tree?companyId=${companyId}`)
    organizationList.value = res.data.data
    // 转换为扁平数组
    flatOrganizationList.value = flattenOrganizations(organizationList.value)
  } catch (error) {
    console.error('获取组织数据失败：', error)
    ElMessage.error('获取组织数据失败')
  }
}

// 获取组织树形数据
const getOrganizationData = async () => {
  if (selectedCompanyId.value) {
    await handleCompanyChange(selectedCompanyId.value)
  }
}

// 新增组织
const handleAdd = () => {
  dialogTitle.value = '新增组织'
  dialogVisible.value = true
}

// 编辑组织
const handleEdit = (row) => {
  dialogTitle.value = '编辑组织'
  form.value = { ...row }
  dialogVisible.value = true
}

// 删除组织
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确认删除该组织吗？')
    await axios.delete(`http://localhost:8080/api/organization/${row.id}`)
    ElMessage.success('删除成功')
    getOrganizationData()
  } catch (error) {
    console.error('删除组织失败：', error)
    ElMessage.error('删除组织失败')
  }
}

// 切换状态
const handleStatusChange = async (row) => {
  try {
    await axios.put(`http://localhost:8080/api/organization/${row.id}/status`, { status: row.status })
    ElMessage.success('状态更新成功')
  } catch (error) {
    console.error('更新状态失败：', error)
    row.status = row.status === 1 ? 0 : 1 // 恢复状态
    ElMessage.error('更新状态失败')
  }
}

// 提交表单
const handleSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (form.value.id) {
          await axios.put(`http://localhost:8080/api/organization/${form.value.id}`, form.value)
          ElMessage.success('更新成功')
        } else {
          await axios.post('http://localhost:8080/api/organization', form.value)
          ElMessage.success('添加成功')
        }
        dialogVisible.value = false
        getOrganizationData()
      } catch (error) {
        console.error('保存组织失败：', error)
        ElMessage.error('保存组织失败')
      }
    }
  })
}

// 重置表单
const resetForm = () => {
  if (formRef.value) {
    formRef.value.resetFields()
  }
  form.value = {
    id: null,
    name: '',
    parentId: null,
    level: 1,
    status: 1,
    companyId: currentCompany.value?.id
  }
}

onMounted(() => {
  getCompanyList()
})
</script>

<style scoped>
.organization-manage {
  padding: 20px;
}

.company-selector {
  margin-bottom: 20px;
}

.company-info {
  margin-bottom: 20px;
  padding: 15px;
  background-color: #f5f7fa;
  border-radius: 4px;
}

.company-info h2 {
  margin: 0 0 10px 0;
  font-size: 18px;
  color: #303133;
}

.company-info p {
  margin: 0;
  color: #606266;
}

.operation-bar {
  margin-bottom: 20px;
}
</style>