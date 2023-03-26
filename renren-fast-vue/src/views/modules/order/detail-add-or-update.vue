<template>
  <el-dialog
    :title="!dataForm.orderDetailId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="订单表ID" prop="orderId">
      <el-input v-model="dataForm.orderId" placeholder="订单表ID"></el-input>
    </el-form-item>
    <el-form-item label="订单商品ID" prop="productId">
      <el-input v-model="dataForm.productId" placeholder="订单商品ID"></el-input>
    </el-form-item>
    <el-form-item label="商品名称" prop="productName">
      <el-input v-model="dataForm.productName" placeholder="商品名称"></el-input>
    </el-form-item>
    <el-form-item label="购买商品份数" prop="productCnt">
      <el-input v-model="dataForm.productCnt" placeholder="购买商品份数"></el-input>
    </el-form-item>
    <el-form-item label="购买商品单价" prop="productPrice">
      <el-input v-model="dataForm.productPrice" placeholder="购买商品单价"></el-input>
    </el-form-item>
    <el-form-item label="商品重量" prop="weight">
      <el-input v-model="dataForm.weight" placeholder="商品重量"></el-input>
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
          orderDetailId: 0,
          orderId: '',
          productId: '',
          productName: '',
          productCnt: '',
          productPrice: '',
          weight: ''
        },
        dataRule: {
          orderId: [
            { required: true, message: '订单表ID不能为空', trigger: 'blur' }
          ],
          productId: [
            { required: true, message: '订单商品ID不能为空', trigger: 'blur' }
          ],
          productName: [
            { required: true, message: '商品名称不能为空', trigger: 'blur' }
          ],
          productCnt: [
            { required: true, message: '购买商品份数不能为空', trigger: 'blur' }
          ],
          productPrice: [
            { required: true, message: '购买商品单价不能为空', trigger: 'blur' }
          ],
          weight: [
            { required: true, message: '商品重量不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.orderDetailId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.orderDetailId) {
            this.$http({
              url: this.$http.adornUrl(`/order/detail/info/${this.dataForm.orderDetailId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.orderId = data.detail.orderId
                this.dataForm.productId = data.detail.productId
                this.dataForm.productName = data.detail.productName
                this.dataForm.productCnt = data.detail.productCnt
                this.dataForm.productPrice = data.detail.productPrice
                this.dataForm.weight = data.detail.weight
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
              url: this.$http.adornUrl(`/order/detail/${!this.dataForm.orderDetailId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'orderDetailId': this.dataForm.orderDetailId || undefined,
                'orderId': this.dataForm.orderId,
                'productId': this.dataForm.productId,
                'productName': this.dataForm.productName,
                'productCnt': this.dataForm.productCnt,
                'productPrice': this.dataForm.productPrice,
                'weight': this.dataForm.weight
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
