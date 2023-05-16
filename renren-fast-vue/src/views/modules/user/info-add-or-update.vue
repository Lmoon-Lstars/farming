<template>
  <el-dialog
    :title="!dataForm.userInfoId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="昵称" prop="nickName">
        <el-input v-model="dataForm.nickName" placeholder=""></el-input>
      </el-form-item>
      <el-form-item label="电话号码" prop="mobilePhone">
        <el-input v-model="dataForm.mobilePhone" placeholder=""></el-input>
      </el-form-item>
      <el-form-item label="种源点" prop="plantPoint">
        <el-input v-model="dataForm.plantPoint" placeholder=""></el-input>
      </el-form-item>
      <el-form-item label="用户状态" prop="userState">
        <el-radio-group v-model="dataForm.userState">
          <el-radio :label="0">禁用</el-radio>
          <el-radio :label="1">正常</el-radio>
        </el-radio-group>
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
        userInfoId: 0,
        avatarUrl: '',
        openId: '',
        plantPoint: '',
        mobilePhone: '',
        nickName: '',
        registerTime: '',
        userState: ''
      },
      dataRule: {
        plantPoint: [
          {required: true, message: '种源点不能为空', trigger: 'blur'}
        ],
        mobilePhone: [
          {required: true, message: '手机号码不能为空', trigger: 'blur'}
        ],
        nickName: [
          {required: true, message: '昵称不能为空', trigger: 'blur'}
        ],
        userState: [
          {required: true, message: '', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    init (id) {
      this.dataForm.userInfoId = id || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.userInfoId) {
          this.$http({
            url: this.$http.adornUrl(`/user/info/info/${this.dataForm.userInfoId}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.avatarUrl = data.info.avatarUrl
              this.dataForm.openId = data.info.openId
              this.dataForm.plantPoint = data.info.plantPoint
              this.dataForm.mobilePhone = data.info.mobilePhone
              this.dataForm.nickName = data.info.nickName
              this.dataForm.registerTime = data.info.registerTime
              this.dataForm.userState = data.info.userState
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
            url: this.$http.adornUrl(`/user/info/${!this.dataForm.userInfoId ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'userInfoId': this.dataForm.userInfoId || undefined,
              'plantPoint': this.dataForm.plantPoint,
              'mobilePhone': this.dataForm.mobilePhone,
              'nickName': this.dataForm.nickName,
              'userState': this.dataForm.userState
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
