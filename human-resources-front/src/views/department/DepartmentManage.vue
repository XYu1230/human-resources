<template>
  <div class="department-manage">
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
      <el-button type="primary" @click="handleAdd">新增部门</el-button>
    </div>
    <el-table
      :data="departmentList"
      row-key="id"
      border
      default-expand-all
      :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
    >
      <el-table-column prop="name" label="部门名称" width="180" />
      <el-table-column prop="code" label="部门编码" width="120" />
      <el-table-column prop="leaderName" label="部门负责人" width="120" />
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
        <el-form-item label="部门名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入部门名称" />
        </el-form-item>
        <el-form-item label="部门编码" prop="code">
          <el-input v-model="form.code" placeholder="请输入部门编码" />
        </el-form-item>
        <el-form-item label="所属公司" prop="companyId">
          <el-input
            v-model="companyName"
            disabled
            placeholder="自动关联当前公司"
          />
        </el-form-item>
        <el-form-item label="上级部门" prop="parentId">
          <el-tree-select
            v-model="form.parentId"
            :data="departmentList"
            :props="{ label: 'name', value: 'id' }"
            placeholder="请选择上级部门"
            clearable
          />
        </el-form-item>
        <el-form-item label="部门负责人" prop="leaderId">
          <el-select v-model="form.leaderId" placeholder="请选择部门负责人" clearable>
            <el-option
              v-for="item in employeeList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-switch
            v-model="form.status"
            :active-value="1"
            :inactive-value="0"
            active-text="启用"
            inactive-text="禁用"
          />
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
import { ref, onMounted, computed } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
// import axios from 'axios' // Removed axios import
// Added departmentApi import - assuming named exports
import {
  getDepartmentList,
  addDepartment,
  updateDepartment,
  deleteDepartment,
  toggleDepartmentStatus,
  // getDepartmentTree, // Assuming not used directly in this component for now
  // getDepartmentDetail // Assuming not used directly in this component for now
} from '@/api/department.js'
// Assuming companyApi is needed for company list/details
import companyApi from '@/api/company.js'
// Assuming employee related API might be needed (using a placeholder)
// import { getSimpleEmployeeList } from '@/api/employee.js'

const departmentList = ref([])
const employeeList = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref(null)
const selectedCompanyId = ref(null)
const companyList = ref([])
const currentCompany = ref(null)

// 获取公司列表
const getCompanyList = async () => {
  try {
    // const res = await axios.get('http://localhost:8080/api/company/list')
    const res = await companyApi.getCompanyList()
    companyList.value = res.data.records
    if (companyList.value.length > 0 && !selectedCompanyId.value) {
      selectedCompanyId.value = companyList.value[0].id
      await handleCompanyChange(selectedCompanyId.value)
    }
  } catch (error) {
    console.error('获取公司列表失败：', error)
    ElMessage.error('获取公司列表失败')
  }
}

// 切换公司
const handleCompanyChange = async (companyId) => {
  try {
    // 获取当前选中的公司信息
    // const res = await axios.get(`http://localhost:8080/api/company/${companyId}`)
    const res = await companyApi.getCompanyDetail(companyId)
    currentCompany.value = res.data
    // 重新获取部门数据
    getDepartmentData()
    // 获取员工列表
    getEmployeeList()
  } catch (error) {
    console.error('获取公司信息失败：', error)
    ElMessage.error('获取公司信息失败')
  }
}

// 修改获取部门数据的方法
const getDepartmentData = async () => {
  if (!selectedCompanyId.value) {
    departmentList.value = []
    return
  }
  try {
    // const res = await axios.get(`http://localhost:8080/api/department/list/${selectedCompanyId.value}`)
    const res = await getDepartmentList(selectedCompanyId.value)
    departmentList.value = res.data
  } catch (error) {
    console.error('获取部门数据失败：', error)
    ElMessage.error('获取部门数据失败')
  }
}

// 修改表单数据
const form = ref({
  id: null,
  name: '',
  code: '',
  companyId: null,
  parentId: null,
  leaderId: null,
  status: 1
})

// 提交表单
const handleSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        const formData = { ...form.value, companyId: selectedCompanyId.value }
        if (formData.id) {
          // await axios.put(`http://localhost:8080/api/department/${formData.id}`, formData)
          await updateDepartment(formData.id, formData)
          ElMessage.success('更新成功')
        } else {
          // await axios.post('http://localhost:8080/api/department', formData)
          await addDepartment(formData)
          ElMessage.success('添加成功')
        }
        dialogVisible.value = false
        getDepartmentData()
      } catch (error) {
        console.error('保存部门失败：', error)
        ElMessage.error('保存部门失败')
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
    code: '',
    companyId: selectedCompanyId.value,
    parentId: null,
    leaderId: null,
    status: 1
  }
}

// 新增部门
const handleAdd = () => {
  dialogTitle.value = '新增部门'
  form.value.companyId = selectedCompanyId.value
  dialogVisible.value = true
}

// 编辑部门
const handleEdit = (row) => {
  dialogTitle.value = '编辑部门'
  form.value = { ...row }
  dialogVisible.value = true
}

// 删除部门
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确认删除该部门吗？')
    // await axios.delete(`http://localhost:8080/api/department/${row.id}`)
    await deleteDepartment(row.id)
    ElMessage.success('删除成功')
    getDepartmentData()
  } catch (error) {
     if (error !== 'cancel') {
      console.error('删除部门失败：', error)
      ElMessage.error('删除部门失败')
    }
  }
}

// 切换状态
const handleStatusChange = async (row) => {
  const newStatus = row.status === 1 ? 0 : 1
  try {
    // await axios.put(`http://localhost:8080/api/department/${row.id}/status`, { status: newStatus })
    await toggleDepartmentStatus(row.id, newStatus)
    ElMessage.success('状态更新成功')
    // No need to manually update row.status, getDepartmentData() will refresh
  } catch (error) {
    console.error('更新状态失败：', error)
    // Revert status optimistically on UI error is complex, better to just refetch
    ElMessage.error('更新状态失败')
    getDepartmentData() // Refetch data to show correct state after error
  }
}

// 表单验证规则
const rules = {
  name: [
    { required: true, message: '请输入部门名称', trigger: 'blur' },
    { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
  ],
  code: [
    { required: true, message: '请输入部门编码', trigger: 'blur' },
    { pattern: /^[A-Za-z0-9_-]+$/, message: '部门编码只能包含字母、数字、下划线和连字符', trigger: 'blur' }
  ]
}

// 获取员工列表
const getEmployeeList = async () => {
  if (!selectedCompanyId.value) return
  try {
    // Placeholder: Replace with actual API call using selectedCompanyId.value if needed
    // const res = await getSimpleEmployeeList({ companyId: selectedCompanyId.value });
    // employeeList.value = res.data;
    // Mocking employee list for now
    employeeList.value = [
      { id: 'emp1', name: '张三' },
      { id: 'emp2', name: '李四' }
    ]
    console.log('获取员工列表 (mocked)')
  } catch (error) {
    console.error('获取员工列表失败:', error)
    ElMessage.error('获取员工列表失败')
  }
}

onMounted(() => {
  getCompanyList()
  getDepartmentData()
})
// 在script setup中添加计算属性
const companyName = computed(() => currentCompany.value ? currentCompany.value.name : '')
</script>

<style scoped>
.department-manage {
  padding: 20px;
}

.operation-bar {
  margin-bottom: 20px;
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
}

.company-info p {
  margin: 0;
  color: #606266;
}
</style>