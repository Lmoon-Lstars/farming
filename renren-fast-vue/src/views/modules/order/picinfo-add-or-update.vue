<template>
  <el-dialog
    :title="!dataForm.orderPicId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
    <el-form-item label="订单ID" prop="orderId">
      <el-input v-model="dataForm.orderId" placeholder="订单ID"></el-input>
    </el-form-item>
    <el-form-item label="图片URL" prop="picUrl">
      <el-input v-model="dataForm.picUrl" placeholder="图片URL"></el-input>
    </el-form-item>
    <el-form-item label="是否主图:0.非主图1.主图" prop="isMaster">
      <el-input v-model="dataForm.isMaster" placeholder="是否主图:0.非主图1.主图"></el-input>
    </el-form-item>
    <el-form-item label="图片排序" prop="picOrder">
      <el-input v-model="dataForm.picOrder" placeholder="图片排序"></el-input>
    </el-form-item>
    <el-form-item label="图片是否有效:0无效 1有效" prop="picStatus">
      <el-input v-model="dataForm.picStatus" placeholder="图片是否有效:0无效 1有效"></el-input>
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
  import {isDate} from '../../../utils/validate'

  export default {
    data () {
      return {
        visible: false,
        dataForm: {
          orderPicId: 0,
          orderId: '',
          picUrl: '',
          isMaster: '',
          picOrder: '',
          picStatus: '',
          modifiedTime: ''
        },
        dataRule: {
          orderId: [
            { required: true, message: '订单ID不能为空', trigger: 'blur' }
          ],
          picUrl: [
            { required: true, message: '图片URL不能为空', trigger: 'blur' }
          ],
          isMaster: [
            { required: true, message: '是否主图:0.非主图1.主图不能为空', trigger: 'blur' }
          ],
          picOrder: [
            { required: true, message: '图片排序不能为空', trigger: 'blur' }
          ],
          picStatus: [
            { required: true, message: '图片是否有效:0无效 1有效不能为空', trigger: 'blur' }
          ],
          modifiedTime: [
            { required: true, message: '最后修改时间不能为空', trigger: 'blur' }
          ]
        }
      }
    },
    methods: {
      init (id) {
        this.dataForm.orderPicId = id || 0
        this.visible = true
        this.$nextTick(() => {
          this.$refs['dataForm'].resetFields()
          if (this.dataForm.orderPicId) {
            this.$http({
              url: this.$http.adornUrl(`/order/picinfo/info/${this.dataForm.orderPicId}`),
              method: 'get',
              params: this.$http.adornParams()
            }).then(({data}) => {
              if (data && data.code === 0) {
                this.dataForm.orderId = data.picInfo.orderId
                this.dataForm.picUrl = data.picInfo.picUrl
                this.dataForm.isMaster = data.picInfo.isMaster
                this.dataForm.picOrder = data.picInfo.picOrder
                this.dataForm.picStatus = data.picInfo.picStatus
                this.dataForm.modifiedTime = isDate(data.picInfo.modifiedTime)
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
              url: this.$http.adornUrl(`/order/picinfo/${!this.dataForm.orderPicId ? 'save' : 'update'}`),
              method: 'post',
              data: this.$http.adornData({
                'orderPicId': this.dataForm.orderPicId || undefined,
                'orderId': this.dataForm.orderId,
                'picUrl': this.dataForm.picUrl,
                'isMaster': this.dataForm.isMaster,
                'picOrder': this.dataForm.picOrder,
                'picStatus': this.dataForm.picStatus,
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
