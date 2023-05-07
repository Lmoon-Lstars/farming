<template>
  <el-dialog
    :title="!dataForm.userInfoId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="头像URL" prop="avatarUrl">
      <el-input v-model="dataForm.avatarUrl" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="用户辨识字段" prop="openId">
      <el-input v-model="dataForm.openId" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="种源点" prop="plantPoint">
      <el-input v-model="dataForm.plantPoint" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="电话号码" prop="mobilePhone">
      <el-input v-model="dataForm.mobilePhone" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="昵称" prop="nickName">
      <el-input v-model="dataForm.nickName" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="注册时间" prop="registerTime">
      <el-input v-model="dataForm.registerTime" placeholder=""></el-input>
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
          registerTime: ''
        },
        dataRule: {
          avatarUrl: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          openId: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          plantPoint: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          mobilePhone: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          nickName: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          registerTime: [
            { required: true, message: '不能为空', trigger: 'blur' }
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
                'avatarUrl': this.dataForm.avatarUrl,
                'openId': this.dataForm.openId,
                'plantPoint': this.dataForm.plantPoint,
                'mobilePhone': this.dataForm.mobilePhone,
                'nickName': this.dataForm.nickName,
                'registerTime': this.dataForm.registerTime
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
