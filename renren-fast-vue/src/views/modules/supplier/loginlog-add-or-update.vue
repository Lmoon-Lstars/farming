<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="登录用户ID" prop="supplierId">
      <el-input v-model="dataForm.supplierId" placeholder="登录用户ID"></el-input>
    </el-form-item>
    <el-form-item label="用户登录时间" prop="loginTime">
      <el-input v-model="dataForm.loginTime" placeholder="用户登录时间"></el-input>
    </el-form-item>
    <el-form-item label="登录IP" prop="loginIp">
      <el-input v-model="dataForm.loginIp" placeholder="登录IP"></el-input>
    </el-form-item>
    <el-form-item label="登录类型:0未成功 1成功" prop="loginType">
      <el-input v-model="dataForm.loginType" placeholder="登录类型:0未成功 1成功"></el-input>
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
          loginId: 0,
          supplierId: '',
          loginTime: '',
          loginIp: '',
          loginType: ''
        },
        dataRule: {
          supplierId: [
            { required: true, message: '登录用户ID不能为空', trigger: 'blur' }
          ],
          loginTime: [
            { required: true, message: '用户登录时间不能为空', trigger: 'blur' }
          ],
          loginIp: [
            { required: true, message: '登录IP不能为空', trigger: 'blur' }
          ],
          loginType: [
            { required: true, message: '登录类型:0未成功 1成功不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.loginId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.loginId) {
            this.$http({
              url: this.$http.adornUrl(`/supplier/loginlog/info/${this.dataForm.loginId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.supplierId = data.loginLog.supplierId
                this.dataForm.loginTime = data.loginLog.loginTime
                this.dataForm.loginIp = data.loginLog.loginIp
                this.dataForm.loginType = data.loginLog.loginType
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
              url: this.$http.adornUrl(`/supplier/loginlog/${!this.dataForm.loginId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'loginId': this.dataForm.loginId || undefined,
                'supplierId': this.dataForm.supplierId,
                'loginTime': this.dataForm.loginTime,
                'loginIp': this.dataForm.loginIp,
                'loginType': this.dataForm.loginType
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
