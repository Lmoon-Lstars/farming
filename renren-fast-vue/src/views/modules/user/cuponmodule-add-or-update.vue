<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="110px">
    <el-form-item label="折扣券" prop="cuponName">
      <el-input v-model="dataForm.cuponName" placeholder=""></el-input>
    </el-form-item>
      <el-form-item label="优惠金额" prop="cuponAmount">
        <el-input v-model="dataForm.cuponAmount" placeholder=""></el-input>
      </el-form-item>
    <el-form-item label="使用门槛金额" prop="minPoint">
      <el-input v-model="dataForm.minPoint" placeholder=""></el-input>
    </el-form-item>
      <el-form-item label="活动时间">
        <el-col :span="11">
          <el-form-item prop="startTime">
            <el-date-picker type="datetime" placeholder="请选择折扣券开始日期" v-model="dataForm.startTime"
                            style="width: 100%;"></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :span="2" align="middle">-</el-col>
        <el-col :span="11">
          <el-form-item prop="endTime">
            <el-date-picker type="datetime" placeholder="请选择折扣券结束日期" v-model="dataForm.endTime"
                            style="width: 100%;"></el-date-picker>
          </el-form-item>
        </el-col>
      </el-form-item>
    <el-form-item label="使用规则" prop="cuponRules">
      <el-input type="textarea" v-model="dataForm.cuponRules" placeholder=""></el-input>
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
            { required: true, message: '折扣券名称不能为空', trigger: 'blur' }
          ],
          minPoint: [
            { required: true, message: '折扣券门槛金额不能为空', trigger: 'blur' }
          ],
          cuponAmount: [
            { required: true, message: '折扣券优惠金额不能为空', trigger: 'blur' }
          ],
          startTime: [
            { required: true, message: '开始时间不能为空', trigger: 'blur' }
          ],
          endTime: [
            { required: true, message: '结束时间不能为空', trigger: 'blur' }
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
