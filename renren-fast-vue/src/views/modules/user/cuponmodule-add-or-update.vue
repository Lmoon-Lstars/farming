<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="" prop="cuponName">
      <el-input v-model="dataForm.cuponName" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="minPoint">
      <el-input v-model="dataForm.minPoint" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="cuponAmount">
      <el-input v-model="dataForm.cuponAmount" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="startTime">
      <el-input v-model="dataForm.startTime" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="endTime">
      <el-input v-model="dataForm.endTime" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="cuponRules">
      <el-input v-model="dataForm.cuponRules" placeholder=""></el-input>
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
          id: 0,
          cuponName: '',
          minPoint: '',
          cuponAmount: '',
          startTime: '',
          endTime: '',
          cuponRules: ''
        },
        dataRule: {
          cuponName: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          minPoint: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          cuponAmount: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          startTime: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          endTime: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          cuponRules: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.id = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.id) {
            this.$http({
              url: this.$http.adornUrl(`/user/cuponmodule/info/${this.dataForm.id}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.cuponName = data.cuponModule.cuponName
                this.dataForm.minPoint = data.cuponModule.minPoint
                this.dataForm.cuponAmount = data.cuponModule.cuponAmount
                this.dataForm.startTime = data.cuponModule.startTime
                this.dataForm.endTime = data.cuponModule.endTime
                this.dataForm.cuponRules = data.cuponModule.cuponRules
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
              url: this.$http.adornUrl(`/user/cuponmodule/${!this.dataForm.id ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'id': this.dataForm.id || undefined,
                'cuponName': this.dataForm.cuponName,
                'minPoint': this.dataForm.minPoint,
                'cuponAmount': this.dataForm.cuponAmount,
                'startTime': this.dataForm.startTime,
                'endTime': this.dataForm.endTime,
                'cuponRules': this.dataForm.cuponRules
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
