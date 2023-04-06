<template>
  <el-dialog
    :title="!dataForm.addressId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="收货人" prop="shippingUser">
      <el-input v-model="dataForm.shippingUser" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="省份" prop="province">
      <el-input v-model="dataForm.province" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="城市" prop="city">
      <el-input v-model="dataForm.city" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="城区" prop="district">
      <el-input v-model="dataForm.district" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="具体地址" prop="address">
      <el-input v-model="dataForm.address" placeholder=""></el-input>
    </el-form-item>
    <el-form-item label="用户ID" prop="userInfoId">
      <el-input v-model="dataForm.userInfoId" placeholder=""></el-input>
    </el-form-item>
      <el-form-item label="电话号码" prop="phoneNumber">
        <el-input v-model="dataForm.phoneNumber" placeholder=""></el-input>
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
          addressId: 0,
          shippingUser: '',
          province: '',
          city: '',
          district: '',
          address: '',
          userInfoId: '',
          phoneNumber: ''
        },
        dataRule: {
          shippingUser: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          province: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          city: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          district: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          address: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          userInfoId: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ],
          phoneNumber: [
            { required: true, message: '不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.addressId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.addressId) {
            this.$http({
              url: this.$http.adornUrl(`/user/address/info/${this.dataForm.addressId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.shippingUser = data.address.shippingUser
                this.dataForm.province = data.address.province
                this.dataForm.city = data.address.city
                this.dataForm.district = data.address.district
                this.dataForm.address = data.address.address
                this.dataForm.userInfoId = data.address.userInfoId
                this.dataForm.phoneNumber = data.address.phoneNumber
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
              url: this.$http.adornUrl(`/user/address/${!this.dataForm.addressId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'addressId': this.dataForm.addressId || undefined,
                'shippingUser': this.dataForm.shippingUser,
                'province': this.dataForm.province,
                'city': this.dataForm.city,
                'district': this.dataForm.district,
                'address': this.dataForm.address,
                'userInfoId': this.dataForm.userInfoId,
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
