<template>
  <el-dialog
    :title="!dataForm.commentId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="评论内容" prop="content">
      <el-input v-model="dataForm.content" placeholder="评论内容"></el-input>
    </el-form-item>
    <el-form-item label="评论者id" prop="userId">
      <el-input v-model="dataForm.userId" placeholder="评论者id"></el-input>
    </el-form-item>
    <el-form-item label="评论商品id" prop="productId">
      <el-input v-model="dataForm.productId" placeholder="评论商品id"></el-input>
    </el-form-item>
    <el-form-item label="评论父id" prop="pid">
      <el-input v-model="dataForm.pid" placeholder="评论父id"></el-input>
    </el-form-item>
    <el-form-item label="被评论人id" prop="replyUserId">
      <el-input v-model="dataForm.replyUserId" placeholder="被评论人id"></el-input>
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
          commentId: 0,
          content: '',
          userId: '',
          productId: '',
          pid: '',
          replyUserId: ''
        },
        dataRule: {
          content: [
            { required: true, message: '评论内容不能为空', trigger: 'blur' }
          ],
          userId: [
            { required: true, message: '评论者id不能为空', trigger: 'blur' }
          ],
          productId: [
            { required: true, message: '评论商品id不能为空', trigger: 'blur' }
          ],
          pid: [
            { required: true, message: '评论父id不能为空', trigger: 'blur' }
          ],
          replyUserId: [
            { required: true, message: '被评论人id不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.commentId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.commentId) {
            this.$http({
              url: this.$http.adornUrl(`/order/commentinfo/info/${this.dataForm.commentId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.content = data.commentInfo.content
                this.dataForm.userId = data.commentInfo.userId
                this.dataForm.productId = data.commentInfo.productId
                this.dataForm.pid = data.commentInfo.pid
                this.dataForm.replyUserId = data.commentInfo.replyUserId
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
              url: this.$http.adornUrl(`/order/commentinfo/${!this.dataForm.commentId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'commentId': this.dataForm.commentId || undefined,
                'content': this.dataForm.content,
                'userId': this.dataForm.userId,
                'productId': this.dataForm.productId,
                'pid': this.dataForm.pid,
                'replyUserId': this.dataForm.replyUserId
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
