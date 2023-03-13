<template>
  <el-dialog
    :title="!dataForm.orderId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="订单编号 yyyymmddnnnnnnnn" prop="orderSn">
      <el-input v-model="dataForm.orderSn" placeholder="订单编号 yyyymmddnnnnnnnn"></el-input>
    </el-form-item>
    <el-form-item label="供应商id" prop="supplierId">
      <el-input v-model="dataForm.supplierId" placeholder="供应商id"></el-input>
    </el-form-item>
    <el-form-item label="下单人ID" prop="customerId">
      <el-input v-model="dataForm.customerId" placeholder="下单人ID"></el-input>
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
    <el-form-item label="支付方式:1现金,2余额,3网银,4支付宝,5微信" prop="paymentMethod">
      <el-input v-model="dataForm.paymentMethod" placeholder="支付方式:1现金,2余额,3网银,4支付宝,5微信"></el-input>
    </el-form-item>
    <el-form-item label="订单金额" prop="orderMoney">
      <el-input v-model="dataForm.orderMoney" placeholder="订单金额"></el-input>
    </el-form-item>
    <el-form-item label="优惠金额" prop="districtMoney">
      <el-input v-model="dataForm.districtMoney" placeholder="优惠金额"></el-input>
    </el-form-item>
    <el-form-item label="运费金额" prop="shippingMoney">
      <el-input v-model="dataForm.shippingMoney" placeholder="运费金额"></el-input>
    </el-form-item>
    <el-form-item label="支付金额" prop="paymentMoney">
      <el-input v-model="dataForm.paymentMoney" placeholder="支付金额"></el-input>
    </el-form-item>
    <el-form-item label="快递公司名称" prop="shippingCompName">
      <el-input v-model="dataForm.shippingCompName" placeholder="快递公司名称"></el-input>
    </el-form-item>
    <el-form-item label="快递单号" prop="shippingSn">
      <el-input v-model="dataForm.shippingSn" placeholder="快递单号"></el-input>
    </el-form-item>
    <el-form-item label="下单时间" prop="createTime">
      <el-input v-model="dataForm.createTime" placeholder="下单时间"></el-input>
    </el-form-item>
    <el-form-item label="发货时间" prop="shippingTime">
      <el-input v-model="dataForm.shippingTime" placeholder="发货时间"></el-input>
    </el-form-item>
    <el-form-item label="支付时间" prop="payTime">
      <el-input v-model="dataForm.payTime" placeholder="支付时间"></el-input>
    </el-form-item>
    <el-form-item label="收货时间" prop="receiveTime">
      <el-input v-model="dataForm.receiveTime" placeholder="收货时间"></el-input>
    </el-form-item>
    <el-form-item label="订单状态(1未支付，2已支付，3已发货，4运输中，5已送达，6已完成)" prop="orderStatus">
      <el-input v-model="dataForm.orderStatus" placeholder="订单状态(1未支付，2已支付，3已发货，4运输中，5已送达，6已完成)"></el-input>
    </el-form-item>
    <el-form-item label="发票抬头" prop="invoiceTitle">
      <el-input v-model="dataForm.invoiceTitle" placeholder="发票抬头"></el-input>
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
          orderId: 0,
          orderSn: '',
          supplierId: '',
          customerId: '',
          shippingUser: '',
          province: '',
          city: '',
          district: '',
          address: '',
          paymentMethod: '',
          orderMoney: '',
          districtMoney: '',
          shippingMoney: '',
          paymentMoney: '',
          shippingCompName: '',
          shippingSn: '',
          createTime: '',
          shippingTime: '',
          payTime: '',
          receiveTime: '',
          orderStatus: '',
          invoiceTitle: '',
          modifiedTime: ''
        },
        dataRule: {
          orderSn: [
            { required: true, message: '订单编号 yyyymmddnnnnnnnn不能为空', trigger: 'blur' }
          ],
          supplierId: [
            { required: true, message: '供应商id不能为空', trigger: 'blur' }
          ],
          customerId: [
            { required: true, message: '下单人ID不能为空', trigger: 'blur' }
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
          paymentMethod: [
            { required: true, message: '支付方式:1现金,2余额,3网银,4支付宝,5微信不能为空', trigger: 'blur' }
          ],
          orderMoney: [
            { required: true, message: '订单金额不能为空', trigger: 'blur' }
          ],
          districtMoney: [
            { required: true, message: '优惠金额不能为空', trigger: 'blur' }
          ],
          shippingMoney: [
            { required: true, message: '运费金额不能为空', trigger: 'blur' }
          ],
          paymentMoney: [
            { required: true, message: '支付金额不能为空', trigger: 'blur' }
          ],
          shippingCompName: [
            { required: true, message: '快递公司名称不能为空', trigger: 'blur' }
          ],
          shippingSn: [
            { required: true, message: '快递单号不能为空', trigger: 'blur' }
          ],
          createTime: [
            { required: true, message: '下单时间不能为空', trigger: 'blur' }
          ],
          shippingTime: [
            { required: true, message: '发货时间不能为空', trigger: 'blur' }
          ],
          payTime: [
            { required: true, message: '支付时间不能为空', trigger: 'blur' }
          ],
          receiveTime: [
            { required: true, message: '收货时间不能为空', trigger: 'blur' }
          ],
          orderStatus: [
            { required: true, message: '订单状态(1未支付，2已支付，3已发货，4运输中，5已送达，6已完成)不能为空', trigger: 'blur' }
          ],
          invoiceTitle: [
            { required: true, message: '发票抬头不能为空', trigger: 'blur' }
          ],
          modifiedTime: [
            { required: true, message: '最后修改时间不能为空', trigger: 'blur' }
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
              url: this.$http.adornUrl(`/order/master/info/${this.dataForm.orderId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.orderSn = data.master.orderSn
                this.dataForm.supplierId = data.master.supplierId
                this.dataForm.customerId = data.master.customerId
                this.dataForm.shippingUser = data.master.shippingUser
                this.dataForm.province = data.master.province
                this.dataForm.city = data.master.city
                this.dataForm.district = data.master.district
                this.dataForm.address = data.master.address
                this.dataForm.paymentMethod = data.master.paymentMethod
                this.dataForm.orderMoney = data.master.orderMoney
                this.dataForm.districtMoney = data.master.districtMoney
                this.dataForm.shippingMoney = data.master.shippingMoney
                this.dataForm.paymentMoney = data.master.paymentMoney
                this.dataForm.shippingCompName = data.master.shippingCompName
                this.dataForm.shippingSn = data.master.shippingSn
                this.dataForm.createTime = data.master.createTime
                this.dataForm.shippingTime = data.master.shippingTime
                this.dataForm.payTime = data.master.payTime
                this.dataForm.receiveTime = data.master.receiveTime
                this.dataForm.orderStatus = data.master.orderStatus
                this.dataForm.invoiceTitle = data.master.invoiceTitle
                this.dataForm.modifiedTime = data.master.modifiedTime
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
              url: this.$http.adornUrl(`/order/master/${!this.dataForm.orderId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'orderId': this.dataForm.orderId || undefined,
                'orderSn': this.dataForm.orderSn,
                'supplierId': this.dataForm.supplierId,
                'customerId': this.dataForm.customerId,
                'shippingUser': this.dataForm.shippingUser,
                'province': this.dataForm.province,
                'city': this.dataForm.city,
                'district': this.dataForm.district,
                'address': this.dataForm.address,
                'paymentMethod': this.dataForm.paymentMethod,
                'orderMoney': this.dataForm.orderMoney,
                'districtMoney': this.dataForm.districtMoney,
                'shippingMoney': this.dataForm.shippingMoney,
                'paymentMoney': this.dataForm.paymentMoney,
                'shippingCompName': this.dataForm.shippingCompName,
                'shippingSn': this.dataForm.shippingSn,
                'createTime': this.dataForm.createTime,
                'shippingTime': this.dataForm.shippingTime,
                'payTime': this.dataForm.payTime,
                'receiveTime': this.dataForm.receiveTime,
                'orderStatus': this.dataForm.orderStatus,
                'invoiceTitle': this.dataForm.invoiceTitle,
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
