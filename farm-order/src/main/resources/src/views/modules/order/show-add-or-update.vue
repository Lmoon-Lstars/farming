<template>
  <el-dialog
    :title="!dataForm.orderId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="下单人ID" prop="customerId">
      <el-input v-model="dataForm.customerId" placeholder="下单人ID"></el-input>
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
    <el-form-item label="收货人姓名" prop="shippingUser">
      <el-input v-model="dataForm.shippingUser" placeholder="收货人姓名"></el-input>
    </el-form-item>
    <el-form-item label="收货人所在省" prop="province">
      <el-input v-model="dataForm.province" placeholder="收货人所在省"></el-input>
    </el-form-item>
    <el-form-item label="收货人所在市" prop="city">
      <el-input v-model="dataForm.city" placeholder="收货人所在市"></el-input>
    </el-form-item>
    <el-form-item label="收货人所在区" prop="district">
      <el-input v-model="dataForm.district" placeholder="收货人所在区"></el-input>
    </el-form-item>
    <el-form-item label="收货人详细地址" prop="address">
      <el-input v-model="dataForm.address" placeholder="收货人详细地址"></el-input>
    </el-form-item>
    <el-form-item label="订单金额" prop="orderMoney">
      <el-input v-model="dataForm.orderMoney" placeholder="订单金额"></el-input>
    </el-form-item>
    <el-form-item label="支付金额" prop="paymentMoney">
      <el-input v-model="dataForm.paymentMoney" placeholder="支付金额"></el-input>
    </el-form-item>
    <el-form-item label="订单状态(1未支付，2已支付，3已发货，4运输中，5已送达，6已完成)" prop="orderStatus">
      <el-input v-model="dataForm.orderStatus" placeholder="订单状态(1未支付，2已支付，3已发货，4运输中，5已送达，6已完成)"></el-input>
    </el-form-item>
    <el-form-item label="备注" prop="remark">
      <el-input v-model="dataForm.remark" placeholder="备注"></el-input>
    </el-form-item>
    <el-form-item label="手机号码" prop="phoneNumber">
      <el-input v-model="dataForm.phoneNumber" placeholder="手机号码"></el-input>
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
          orderId: 0,
          customerId: '',
          productName: '',
          productCnt: '',
          productPrice: '',
          weight: '',
          shippingUser: '',
          province: '',
          city: '',
          district: '',
          address: '',
          orderMoney: '',
          paymentMoney: '',
          orderStatus: '',
          remark: '',
          phoneNumber: ''
        },
        dataRule: {
          customerId: [
            { required: true, message: '下单人ID不能为空', trigger: 'blur' }
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
          ],
          shippingUser: [
            { required: true, message: '收货人姓名不能为空', trigger: 'blur' }
          ],
          province: [
            { required: true, message: '收货人所在省不能为空', trigger: 'blur' }
          ],
          city: [
            { required: true, message: '收货人所在市不能为空', trigger: 'blur' }
          ],
          district: [
            { required: true, message: '收货人所在区不能为空', trigger: 'blur' }
          ],
          address: [
            { required: true, message: '收货人详细地址不能为空', trigger: 'blur' }
          ],
          orderMoney: [
            { required: true, message: '订单金额不能为空', trigger: 'blur' }
          ],
          paymentMoney: [
            { required: true, message: '支付金额不能为空', trigger: 'blur' }
          ],
          orderStatus: [
            { required: true, message: '订单状态(1未支付，2已支付，3已发货，4运输中，5已送达，6已完成)不能为空', trigger: 'blur' }
          ],
          remark: [
            { required: true, message: '备注不能为空', trigger: 'blur' }
          ],
          phoneNumber: [
            { required: true, message: '手机号码不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.orderId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.orderId) {
            this.$http({
              url: this.$http.adornUrl(`/order/show/info/${this.dataForm.orderId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.customerId = data.show.customerId
                this.dataForm.productName = data.show.productName
                this.dataForm.productCnt = data.show.productCnt
                this.dataForm.productPrice = data.show.productPrice
                this.dataForm.weight = data.show.weight
                this.dataForm.shippingUser = data.show.shippingUser
                this.dataForm.province = data.show.province
                this.dataForm.city = data.show.city
                this.dataForm.district = data.show.district
                this.dataForm.address = data.show.address
                this.dataForm.orderMoney = data.show.orderMoney
                this.dataForm.paymentMoney = data.show.paymentMoney
                this.dataForm.orderStatus = data.show.orderStatus
                this.dataForm.remark = data.show.remark
                this.dataForm.phoneNumber = data.show.phoneNumber
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
              url: this.$http.adornUrl(`/order/show/${!this.dataForm.orderId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'orderId': this.dataForm.orderId || undefined,
                'customerId': this.dataForm.customerId,
                'productName': this.dataForm.productName,
                'productCnt': this.dataForm.productCnt,
                'productPrice': this.dataForm.productPrice,
                'weight': this.dataForm.weight,
                'shippingUser': this.dataForm.shippingUser,
                'province': this.dataForm.province,
                'city': this.dataForm.city,
                'district': this.dataForm.district,
                'address': this.dataForm.address,
                'orderMoney': this.dataForm.orderMoney,
                'paymentMoney': this.dataForm.paymentMoney,
                'orderStatus': this.dataForm.orderStatus,
                'remark': this.dataForm.remark,
                'phoneNumber': this.dataForm.phoneNumber
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
