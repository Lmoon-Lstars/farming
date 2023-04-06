<template>
  <el-dialog
    :title="!dataForm.typeId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="类型编号" prop="typeCode">
      <el-input v-model="dataForm.typeCode" placeholder="类型编号"></el-input>
    </el-form-item>
    <el-form-item label="类型名称" prop="typeName">
      <el-input v-model="dataForm.typeName" placeholder="类型名称"></el-input>
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
          typeId: 0,
          typeCode: '',
          typeName: ''
        },
        dataRule: {
          typeCode: [
            { required: true, message: '类型编号不能为空', trigger: 'blur' }
          ],
          typeName: [
            { required: true, message: '类型名称不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.typeId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.typeId) {
            this.$http({
              url: this.$http.adornUrl(`/product/type/info/${this.dataForm.typeId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.typeCode = data.type.typeCode
                this.dataForm.typeName = data.type.typeName
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
              url: this.$http.adornUrl(`/product/type/${!this.dataForm.typeId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'typeId': this.dataForm.typeId || undefined,
                'typeCode': this.dataForm.typeCode,
                'typeName': this.dataForm.typeName
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
