<template>
  <el-dialog
    :title="!dataForm.id ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="80px">
      <el-form-item label="商品图片" prop="productPicFile">
        <!--        <el-input v-model="dataForm.productPicFile" v-if="false"></el-input>-->
        <el-upload
          ref="uploadPic"
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
        id: 0,
        imageurl: '',
        productPicFile: ''
      },
      dataRule: {
        productPicFile: [
          { required: true, message: '请上传图片', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    init (id) {
      this.dataForm.id = id || 0
      this.visible = true
      this.$refs.uploadPic.clearFiles()
    },
    // 上传文件时的改变时的方法
    uploadFile (file, fileList) {
      this.dataForm.productPicFile = file.raw  // 拿到文件的信息
    },
    // 删除照片
    handleRemove (file, fileList) {
      this.dataForm.productPicFile = ''
    },
    // 处理照片url
    handlePictureCardPreview (file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(`/user/banner/receive`),
            method: 'post',
            data: this.$http.myFormData({
              'id': this.dataForm.id || undefined,
              'file': this.dataForm.productPicFile
            }, true, 'x-www-form-urlencoded')
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
