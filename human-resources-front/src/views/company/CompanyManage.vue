<template>
  <div class="company-container">
    <div class="search-container">
      <el-form :inline="true" :model="searchForm" class="demo-form-inline">
        <el-form-item label="企业名称">
          <el-input v-model="searchForm.name" placeholder="请输入企业名称" clearable />
        </el-form-item>
        <el-form-item label="企业编码">
          <el-input v-model="searchForm.code" placeholder="请输入企业编码" clearable />
        </el-form-item>
        <el-form-item label="联系人">
          <el-input v-model="searchForm.contactPerson" placeholder="请输入联系人姓名" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="resetSearch">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    
    <div class="operation-container">
      <el-button type="primary" @click="handleAdd">新增企业</el-button>
    </div>
    
    <el-table :data="tableData" style="width: 100%" v-loading="loading">
      <el-table-column prop="id" label="企业ID" width="80" />
      <el-table-column prop="name" label="企业名称" width="180" />
      <el-table-column prop="code" label="企业编码" width="120" />
      <el-table-column prop="address" label="企业地址" />
      <el-table-column prop="contactPerson" label="联系人" width="120" />
      <el-table-column prop="contactPhone" label="联系电话" width="120" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="scope">
          <el-tag :type="scope.row.status === 1 ? 'success' : 'danger'">
            {{ scope.row.status === 1 ? '启用' : '禁用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="220">
        <template #default="scope">
          <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          <el-button 
            size="small" 
            :type="scope.row.status === 1 ? 'warning' : 'success'"
            @click="handleStatusChange(scope.row)"
          >
            {{ scope.row.status === 1 ? '禁用' : '启用' }}
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <div class="pagination-container">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 30, 50]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
    
    <!-- 新增/编辑企业对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="dialogType === 'add' ? '新增企业' : '编辑企业'"
      width="50%"
    >
      <el-form
        ref="companyFormRef"
        :model="companyForm"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="企业名称" prop="name">
          <el-input v-model="companyForm.name" placeholder="请输入企业名称" />
        </el-form-item>
        <el-form-item label="企业编码" prop="code">
          <el-input v-model="companyForm.code" placeholder="请输入企业编码" />
        </el-form-item>
        <el-form-item label="企业地址" prop="address">
          <el-input v-model="companyForm.address" placeholder="请输入企业地址" />
        </el-form-item>
        <el-form-item label="联系人" prop="contactPerson">
          <el-input v-model="companyForm.contactPerson" placeholder="请输入联系人姓名" />
        </el-form-item>
        <el-form-item label="联系电话" prop="contactPhone">
          <el-input v-model="companyForm.contactPhone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="companyForm.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import companyApi from '@/api/company.js'

// 表格数据
const tableData = ref([])
const loading = ref(false)
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)

// 搜索表单
const searchForm = reactive({
  name: '',
  code: '',
  contactPerson: ''
})

// 企业表单
const companyFormRef = ref(null)
const companyForm = reactive({
  id: null,
  name: '',
  code: '',
  address: '',
  contactPerson: '',
  contactPhone: '',
  status: 1
})

// 表单验证规则
const rules = {
  name: [
    { required: true, message: '请输入企业名称', trigger: 'blur' },
    { min: 2, max: 100, message: '长度在 2 到 100 个字符', trigger: 'blur' }
  ],
  code: [
    { required: true, message: '请输入企业编码', trigger: 'blur' },
    { min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: 'blur' }
  ],
  contactPhone: [
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
  ]
}

// 对话框控制
const dialogVisible = ref(false)
const dialogType = ref('add') // 'add' 或 'edit'

// 初始化加载数据
onMounted(() => {
  fetchCompanyList()
})

// 获取企业列表数据
const fetchCompanyList = async () => {
  loading.value = true
  try {
    const params = {
      page: currentPage.value,
      size: pageSize.value,
      ...searchForm
    }
    
    const response = await companyApi.getCompanyList(params)
    tableData.value = response.data.records || []
    total.value = response.data.total || 0
    console.log('获取企业列表成功:', response.data.records)
  } catch (error) {
    console.error('获取企业列表失败:', error)
    ElMessage.error('获取企业列表失败')
  } finally {
    loading.value = false
  }
}

// 搜索处理
const handleSearch = () => {
  currentPage.value = 1
  fetchCompanyList()
}

// 重置搜索
const resetSearch = () => {
  Object.keys(searchForm).forEach(key => {
    searchForm[key] = ''
  })
  currentPage.value = 1
  fetchCompanyList()
}

// 分页处理
const handleSizeChange = (val) => {
  pageSize.value = val
  fetchCompanyList()
}

const handleCurrentChange = (val) => {
  currentPage.value = val
  fetchCompanyList()
}

// 新增企业
const handleAdd = () => {
  dialogType.value = 'add'
  resetCompanyForm()
  dialogVisible.value = true
}

// 编辑企业
const handleEdit = (row) => {
  dialogType.value = 'edit'
  resetCompanyForm()
  Object.keys(companyForm).forEach(key => {
    if (key in row) {
      companyForm[key] = row[key]
    }
  })
  dialogVisible.value = true
}

// 重置企业表单
const resetCompanyForm = () => {
  if (companyFormRef.value) {
    companyFormRef.value.resetFields()
  }
  companyForm.id = null
  companyForm.name = ''
  companyForm.code = ''
  companyForm.address = ''
  companyForm.contactPerson = ''
  companyForm.contactPhone = ''
  companyForm.status = 1
}

// 提交表单
const submitForm = async () => {
  if (!companyFormRef.value) return
  
  await companyFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (dialogType.value === 'add') {
          await companyApi.addCompany(companyForm)
          ElMessage.success('新增成功')
        } else {
          await companyApi.updateCompany(companyForm.id, companyForm)
          ElMessage.success('更新成功')
        }
        dialogVisible.value = false
        fetchCompanyList()
      } catch (error) {
        console.error('保存企业失败:', error)
        ElMessage.error('保存企业失败')
      }
    }
  })
}

// 删除企业
const handleDelete = async (row) => {
  try {
    await ElMessageBox.confirm('确认删除该企业吗?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await companyApi.deleteCompany(row.id)
    ElMessage.success('删除成功')
    fetchCompanyList()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除企业失败:', error)
      ElMessage.error('删除企业失败')
    }
  }
}

// 更改状态
const handleStatusChange = async (row) => {
  const newStatus = row.status === 1 ? 0 : 1
  try {
    await ElMessageBox.confirm(`确认要${newStatus === 1 ? '启用' : '禁用'}该企业吗?`, '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    })
    await companyApi.updateCompanyStatus(row.id, newStatus)
    ElMessage.success('状态更改成功')
    fetchCompanyList()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('更改状态失败:', error)
      ElMessage.error('更改状态失败')
    }
  }
}
</script>

<style scoped>
.company-container {
  padding: 20px;
}

.search-container {
  margin-bottom: 20px;
  padding: 15px;
  background-color: #f5f7fa;
  border-radius: 4px;
}

.operation-container {
  margin-bottom: 20px;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>