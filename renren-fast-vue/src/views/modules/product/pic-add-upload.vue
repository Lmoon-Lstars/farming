<template>
  <el-dialog
    :title="!dataForm.productPicId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item label="商品ID" prop="productId">
        <el-input v-model="dataForm.productId" placeholder="商品ID"></el-input>
      </el-form-item>
      <el-form-item label="商品图片" prop="productPicFile">
<!--        <el-input v-model="dataForm.productPicFile" v-if="false"></el-input>-->
        <el-upload
          ref="upload"
          action=""
          :auto-upload="false"
          accept="jpg,jpeg,png,PNG"
          list-type="picture-card"
          :data="dataForm.productId"
          :show-file-list="true"
          :on-change="uploadFile"
          :on-preview="handlePictureCardPreview"
          :on-remove="handleRemove">
          <i class="el-icon-plus"></i>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible" append-to-body="true">
          <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
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
      dialogImageUrl: '',
      dialogVisible: false,
      dataForm: {
        productId: '',
        productPicFile: ''
      },
      dataRule: {
        productId: [
          { required: true, message: '商品ID不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init (id) {
      this.visible = true
      this.$nextTick(() => {
        this.$refs.upload.clearFiles()
        this.$refs['dataForm'].resetFields()
      })
    },
    // 上传文件时的改变时的方法
    uploadFile (file, fileList) {
      console.log('添加文件')
      console.log(file.raw)
      this.dataForm.productPicFile = file.raw  // 拿到文件的信息
      console.log('表单文件 productPicFile')
      console.log('proId ' + this.dataForm.productId)
      console.log(this.dataForm.productPicFile)
    },
    // 修改表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(`/product/picinfo/receive`),
            method: 'post',
            data: this.$http.myFormData({
              'id': this.dataForm.productId,
              'file': this.dataForm.productPicFile
            }, true, 'x-www-form-urlencoded'
            )
          }).then(({data}) => {
            console.log(data)
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
          }).catch(({data}) => {
            console.log('数据格式错误')
            console.log(data)
          })
        }
      })
    },
    handleRemove (file, fileList) {
      console.log('删除文件')
      console.log(file, fileList)
    },
    handlePictureCardPreview (file) {
      console.log('文件预览 ' + file.url)
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    }
  }
}
</script>
