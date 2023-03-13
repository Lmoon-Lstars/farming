<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="用户登陆名" prop="loginName">
      <el-input v-model="dataForm.loginName" placeholder="用户登陆名"></el-input>
    </el-form-item>
    <el-form-item label="md5加密的密码" prop="password">
      <el-input v-model="dataForm.password" placeholder="md5加密的密码"></el-input>
    </el-form-item>
    <el-form-item label="用户状态（0锁定，1未锁定）" prop="userStats">
      <el-input v-model="dataForm.userStats" placeholder="用户状态（0锁定，1未锁定）"></el-input>
    </el-form-item>
    <el-form-item label="最后修改时间" prop="modifiedTime">
      <el-input v-model="dataForm.modifiedTime" placeholder="最后修改时间"></el-input>
    </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          supplierId: 0,
          loginName: '',
          password: '',
          userStats: '',
          modifiedTime: ''
        },
        dataRule: {
          loginName: [
            { required: true, message: '用户登陆名不能为空', trigger: 'blur' }
          ],
          password: [
            { required: true, message: 'md5加密的密码不能为空', trigger: 'blur' }
          ],
          userStats: [
            { required: true, message: '用户状态（0锁定，1未锁定）不能为空', trigger: 'blur' }
          ],
          modifiedTime: [
            { required: true, message: '最后修改时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.supplierId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.supplierId) {
            this.$http({
              url: this.$http.adornUrl(`/supplier/login/info/${this.dataForm.supplierId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.loginName = data.login.loginName
                this.dataForm.password = data.login.password
                this.dataForm.userStats = data.login.userStats
                this.dataForm.modifiedTime = data.login.modifiedTime
              }
            })
          }
        })
      },
      // 表单提交
      dataFormSubmit () {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/supplier/login/${!this.dataForm.supplierId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'supplierId': this.dataForm.supplierId || undefined,
                'loginName': this.dataForm.loginName,
                'password': this.dataForm.password,
                'userStats': this.dataForm.userStats,
                'modifiedTime': this.dataForm.modifiedTime
              })
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.$message({
                  message: '操作成功',
                  type: 'success',
                  duration: 1500,
                  onClose: () => {
                    this.visible = false
                    this.$emit('refreshDataList')
                  }
                })
              } else {
                this.$message.error(data.msg)
              }
            })
          }
        })
      }
    }
  }
</script>
