<template>
  <el-dialog
    :title="!dataForm.saleId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="" prop="productName">
      <el-input v-model="dataForm.productName" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="productCnt">
      <el-input v-model="dataForm.productCnt" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="year">
      <el-input v-model="dataForm.year" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="month">
      <el-input v-model="dataForm.month" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="" prop="day">
      <el-input v-model="dataForm.day" placeholder=""></el-input>
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
          saleId: 0,
          productName: '',
          productCnt: '',
          year: '',
          month: '',
          day: ''
        },
        dataRule: {
          productName: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          productCnt: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          year: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          month: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          day: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.saleId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.saleId) {
            this.$http({
              url: this.$http.adornUrl(`/order/sale/info/${this.dataForm.saleId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.productName = data.sale.productName
                this.dataForm.productCnt = data.sale.productCnt
                this.dataForm.year = data.sale.year
                this.dataForm.month = data.sale.month
                this.dataForm.day = data.sale.day
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
              url: this.$http.adornUrl(`/order/sale/${!this.dataForm.saleId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'saleId': this.dataForm.saleId || undefined,
                'productName': this.dataForm.productName,
                'productCnt': this.dataForm.productCnt,
                'year': this.dataForm.year,
                'month': this.dataForm.month,
                'day': this.dataForm.day
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
