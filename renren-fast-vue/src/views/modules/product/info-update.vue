<template>
  <div>
    <el-dialog
      title="修改"
      :close-on-click-modal="false"
      :visible.sync="visible">
      <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
               label-width="100px">

        <el-form-item label="商品名称" prop="productName">
          <el-input v-model="dataForm.productName" placeholder="商品名称"></el-input>
        </el-form-item>
        <el-form-item label="销售价格" prop="price">
          <el-input v-model="dataForm.price" placeholder="商品销售价格"></el-input>
        </el-form-item>
        <el-form-item label="上下架" prop="publishStatus">
          <el-switch v-model="dataForm.publishStatus"></el-switch>
        </el-form-item>
        <el-form-item label="优惠" prop="ifShow">
          <el-switch v-model="dataForm.ifShow"></el-switch>
        </el-form-item>
        <el-form-item label="优惠价" prop="disPrice">
          <el-input v-model="dataForm.disPrice" placeholder="请填写优惠价"></el-input>
        </el-form-item>
        <el-form-item label="优惠活动时间">
          <el-col :span="11">
            <el-form-item prop="startTime">
              <el-date-picker type="datetime" placeholder="选择日期" v-model="dataForm.startTime"
                              style="width: 100%;"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="2" align="middle">-</el-col>
          <el-col :span="11">
            <el-form-item prop="endTime">
              <el-date-picker type="datetime" placeholder="选择日期" v-model="dataForm.endTime"
                              style="width: 100%;"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-form-item>


      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button @click="detailedModifications()">详细修改</el-button>
      <el-button type="primary" @click="quickModifications()">确定</el-button>
    </span>
    </el-dialog>
    <info-update-detail v-show="isShowUpdateDetail" ref="updateDetail" @refreshDataList=""></info-update-detail>
  </div>
</template>

<script>
import {eventBus} from '../../../main'
import InfoUpdateDetail from './info-update-detail'

export default {
  components: {InfoUpdateDetail},
  data () {
    return {
      visible: false,
      dataForm: {
        status: '',
        productId: 0,
        productCode: '',
        productName: '',
        price: '',
        supplierId: '',
        freeNum: '',
        supplyNum: '',
        perWeight: '',
        description: '',
        publishStatus: false,
        auditStatus: '',
        place: '',
        breed: '',
        isSpecial: '',
        ifShow: false,
        disPrice: '',
        startTime: '',
        endTime: '',
        typeCode: ''
        // productPicFile: ''
      },
      isShowUpdateDetail: false,
      productTypeList: [],
      dataRule: {
        productName: [
          {required: true, message: '商品名称不能为空', trigger: 'blur'}
        ],
        price: [
          {required: true, message: '商品价格不能为空', trigger: 'blur'}
        ],
        freeNum: [
          {required: true, message: '请填写免费供应份数', trigger: 'blur'}
        ],
        supplyNum: [
          {required: true, message: '请填写正常供应份数', trigger: 'blur'}
        ],
        perWeight: [
          {required: true, message: '请填写每份重量/克', trigger: 'blur'}
        ],
        place: [
          {required: true, message: '产地不能为空', trigger: 'blur'}
        ],
        breed: [
          {required: true, message: '品种不能为空', trigger: 'blur'}
        ],
        typeCode: [
          {required: true, message: '请选择产品类型', trigger: 'blur'}
        ],
        productPicFile: [
          {required: true, message: '请上传商品图片', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    init (id) {
      this.dataForm.productId = id
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.productId) {
          this.$http({
            url: this.$http.adornUrl(`/product/info/info/${this.dataForm.productId}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.productCode = data.info.productCode
              this.dataForm.productName = data.info.productName
              this.dataForm.price = data.info.price
              this.dataForm.supplierId = data.info.supplierId
              this.dataForm.freeNum = data.info.freeNum
              this.dataForm.supplyNum = data.info.supplyNum
              this.dataForm.perWeight = data.info.perWeight
              this.dataForm.description = data.info.description
              this.dataForm.publishStatus = !!data.info.publishStatus
              this.dataForm.auditStatus = data.info.auditStatus
              this.dataForm.place = data.info.place
              this.dataForm.breed = data.info.breed
              this.dataForm.isSpecial = !!data.info.isSpecial
              this.dataForm.ifShow = !!data.info.ifShow
              this.dataForm.startTime = data.info.startTime
              this.dataForm.endTime = data.info.endTime
              this.dataForm.typeCode = data.info.typeCode
            }
          })
          // this.$http({
          //   url: this.$http.adornUrl(`/product/picinfo/getStream`),
          //   method: 'get',
          //   params: this.$http.adornParams(
          //     {
          //       'productId': this.dataForm.productId
          //     }
          //   ),
          //   responseType: 'blob'
          // }).then(({data}) => {
          //   this.dataForm.productPicFile = new File([data], 'screenshot.png', {type: 'image/jpeg'})
          // }).catch(error => {
          //   console.log(error)
          //   this.$message({
          //     message: '系统错误，获取图片失败，请联系系统管理员！',
          //     type: 'error',
          //     center: true
          //   })
          // })
        }
      })
    },
    // 快速修改提交表单
    quickModifications () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(`/product/info/update`),
            method: 'post',
            data: this.$http.adornData({
              'productId': this.dataForm.productId,
              'productName': this.dataForm.productName,
              'price': this.dataForm.price,
              'publishStatus': this.dataForm.publishStatus ? 1 : 0,
              'ifShow': this.dataForm.ifShow ? 1 : 0,
              'disPrice': this.dataForm.disPrice,
              'startTime': this.dataForm.startTime,
              'endTime': this.dataForm.endTime
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
    },
    // 详细修改
    detailedModifications () {
      this.visible = false
      this.$parent.isShowUpdateDetail = true
      eventBus.$emit('send', this.dataForm)
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(`/product/info/${!this.dataForm.productId ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'productId': this.dataForm.productId || undefined,
              'productCode': this.dataForm.productCode,
              'productName': this.dataForm.productName,
              'price': this.dataForm.price,
              'supplierId': this.dataForm.supplierId,
              'freeNum': this.dataForm.freeNum,
              'supplyNum': this.dataForm.supplyNum,
              'perWeight': this.dataForm.perWeight,
              'description': this.dataForm.description,
              'publishStatus': this.dataForm.publishStatus,
              'auditStatus': this.dataForm.auditStatus,
              'place': this.dataForm.place,
              'breed': this.dataForm.breed,
              'isSpecial': this.dataForm.isSpecial,
              'ifShow': this.dataForm.ifShow,
              'disPrice': this.dataForm.disPrice,
              'startTime': this.dataForm.startTime,
              'endTime': this.dataForm.endTime,
              'typeCode': this.dataForm.typeCode
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
