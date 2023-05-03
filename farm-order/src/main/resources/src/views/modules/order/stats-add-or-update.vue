<template>
  <el-dialog
    :title="!dataForm.statsId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="" prop="customerId">
      <el-input v-model="dataForm.customerId" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="orderNum">
      <el-input v-model="dataForm.orderNum" placeholder=""></el-input>
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
          statsId: 0,
          customerId: '',
          orderNum: ''
        },
        dataRule: {
          customerId: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          orderNum: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.statsId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.statsId) {
            this.$http({
              url: this.$http.adornUrl(`/order/stats/info/${this.dataForm.statsId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.customerId = data.stats.customerId
                this.dataForm.orderNum = data.stats.orderNum
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
              url: this.$http.adornUrl(`/order/stats/${!this.dataForm.statsId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'statsId': this.dataForm.statsId || undefined,
                'customerId': this.dataForm.customerId,
                'orderNum': this.dataForm.orderNum
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
