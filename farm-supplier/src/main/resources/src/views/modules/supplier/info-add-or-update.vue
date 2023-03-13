<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="supplier_login表的自增ID" prop="supplierId">
      <el-input v-model="dataForm.supplierId" placeholder="supplier_login表的自增ID"></el-input>
    </el-form-item>
    <el-form-item label="用户真实姓名" prop="supplierName">
      <el-input v-model="dataForm.supplierName" placeholder="用户真实姓名"></el-input>
    </el-form-item>
    <el-form-item label="证件类型：1 身份证,2军官证,3护照" prop="identityCardType">
      <el-input v-model="dataForm.identityCardType" placeholder="证件类型：1 身份证,2军官证,3护照"></el-input>
    </el-form-item>
    <el-form-item label="证件号码" prop="identityCardNo">
      <el-input v-model="dataForm.identityCardNo" placeholder="证件号码"></el-input>
    </el-form-item>
    <el-form-item label="手机号" prop="mobilePhone">
      <el-input v-model="dataForm.mobilePhone" placeholder="手机号"></el-input>
    </el-form-item>
    <el-form-item label="邮箱" prop="supplierEmail">
      <el-input v-model="dataForm.supplierEmail" placeholder="邮箱"></el-input>
    </el-form-item>
    <el-form-item label="性别" prop="gender">
      <el-input v-model="dataForm.gender" placeholder="性别"></el-input>
    </el-form-item>
    <el-form-item label="注册时间" prop="registerTime">
      <el-input v-model="dataForm.registerTime" placeholder="注册时间"></el-input>
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
          supplierInfoId: 0,
          supplierId: '',
          supplierName: '',
          identityCardType: '',
          identityCardNo: '',
          mobilePhone: '',
          supplierEmail: '',
          gender: '',
          registerTime: '',
          modifiedTime: ''
        },
        dataRule: {
          supplierId: [
            { required: true, message: 'supplier_login表的自增ID不能为空', trigger: 'blur' }
          ],
          supplierName: [
            { required: true, message: '用户真实姓名不能为空', trigger: 'blur' }
          ],
          identityCardType: [
            { required: true, message: '证件类型：1 身份证,2军官证,3护照不能为空', trigger: 'blur' }
          ],
          identityCardNo: [
            { required: true, message: '证件号码不能为空', trigger: 'blur' }
          ],
          mobilePhone: [
            { required: true, message: '手机号不能为空', trigger: 'blur' }
          ],
          supplierEmail: [
            { required: true, message: '邮箱不能为空', trigger: 'blur' }
          ],
          gender: [
            { required: true, message: '性别不能为空', trigger: 'blur' }
          ],
          registerTime: [
            { required: true, message: '注册时间不能为空', trigger: 'blur' }
          ],
          modifiedTime: [
            { required: true, message: '最后修改时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.supplierInfoId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.supplierInfoId) {
            this.$http({
              url: this.$http.adornUrl(`/supplier/info/info/${this.dataForm.supplierInfoId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.supplierId = data.info.supplierId
                this.dataForm.supplierName = data.info.supplierName
                this.dataForm.identityCardType = data.info.identityCardType
                this.dataForm.identityCardNo = data.info.identityCardNo
                this.dataForm.mobilePhone = data.info.mobilePhone
                this.dataForm.supplierEmail = data.info.supplierEmail
                this.dataForm.gender = data.info.gender
                this.dataForm.registerTime = data.info.registerTime
                this.dataForm.modifiedTime = data.info.modifiedTime
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
              url: this.$http.adornUrl(`/supplier/info/${!this.dataForm.supplierInfoId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'supplierInfoId': this.dataForm.supplierInfoId || undefined,
                'supplierId': this.dataForm.supplierId,
                'supplierName': this.dataForm.supplierName,
                'identityCardType': this.dataForm.identityCardType,
                'identityCardNo': this.dataForm.identityCardNo,
                'mobilePhone': this.dataForm.mobilePhone,
                'supplierEmail': this.dataForm.supplierEmail,
                'gender': this.dataForm.gender,
                'registerTime': this.dataForm.registerTime,
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
