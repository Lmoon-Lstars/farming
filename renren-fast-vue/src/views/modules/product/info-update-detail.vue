<template>
  <div>
    <h2 style="margin: 10px 0 30px 0; text-align:center">修改商品</h2>
    <el-form :model="dataForm"
             :rules="dataRule"
             ref="dataForm"
             @keyup.enter.native="dataFormSubmit()"
             label-width="120px">
      <el-row>
        <el-col :span="10">
          <el-form-item label="商品名称" prop="productName">
            <el-input v-model="dataForm.productName" placeholder="请填写商品名称"></el-input>
          </el-form-item>
          <el-form-item label="销售价格" prop="price">
            <el-input v-model="dataForm.price" placeholder="请填写商品销售价格"></el-input>
          </el-form-item>
          <el-form-item label="免费供应份数" prop="freeNum">
            <el-input v-model="dataForm.freeNum" placeholder="请填写免费供应份数"></el-input>
          </el-form-item>
          <el-form-item label="正常供应份数" prop="supplyNum">
            <el-input v-model="dataForm.supplyNum" placeholder="请填写正常供应份数"></el-input>
          </el-form-item>
          <el-form-item label="每份重量/克" prop="perWeight">
            <el-input v-model="dataForm.perWeight" placeholder="请填写每份重量/克"></el-input>
          </el-form-item>
          <el-form-item label="产地" prop="place">
            <el-input v-model="dataForm.place" placeholder="请填写产地"></el-input>
          </el-form-item>
          <el-form-item label="品种" prop="breed">
            <el-input v-model="dataForm.breed" placeholder="请填写品种"></el-input>
          </el-form-item>
          <el-form-item label="产品类型" prop="typeCode">
            <el-select v-model="dataForm.typeCode" placeholder="请选择产品类型">
              <el-option
                v-for="item in productTypeList"
                :key="item.typeId"
                :label="item.typeName"
                :value="item.typeCode">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="特产" prop="isSpecial">
            <el-switch v-model="dataForm.isSpecial"></el-switch>
          </el-form-item>
        </el-col>
        <el-col :span="3">
          <el-divider direction="vertical" style="color: #409EFF"></el-divider>
        </el-col>
        <el-col :span="11" style="float:right;">
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
                <el-date-picker type="datetime" placeholder="请选择日期" v-model="dataForm.startTime"
                                style="width: 100%;"></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="2" align="middle">-</el-col>
            <el-col :span="11">
              <el-form-item prop="endTime">
                <el-date-picker type="datetime" placeholder="请选择日期" v-model="dataForm.endTime"
                                style="width: 100%;"></el-date-picker>
              </el-form-item>
            </el-col>
          </el-form-item>
          <el-form-item label="商品描述" prop="description">
            <el-input type="textarea" v-model="dataForm.description" placeholder="请填写商品描述" :rows="6"></el-input>
          </el-form-item>
          <el-form-item label="商品图片" prop="productPicFile">
            <!--        <el-input v-model="dataForm.productPicFile" v-if="false"></el-input>-->
            <el-upload
              ref="uploadPic"
              action=""
              :auto-upload="false"
              accept="jpg,jpeg,png,PNG"
              list-type="picture-card"
              :limit="1"
              :on-change="uploadFile"
              :on-preview="handlePictureCardPreview"
              :on-remove="handleRemove">
              <i class="el-icon-plus"></i>
            </el-upload>
            <el-dialog :visible.sync="dialogVisible">
              <img width="100%" :src="dialogImageUrl" alt="">
            </el-dialog>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <span style="float:right;margin: 20px">
      <el-button @click="returnList">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </div>
</template>

<script>
import {eventBus} from '../../../main'

export default {
  name: 'info-update-detail',
  data () {
    return {
      dataForm: {
        productId: '',
        productName: '',
        price: '',
        freeNum: '',
        supplyNum: '',
        perWeight: '',
        description: '',
        place: '',
        breed: '',
        isSpecial: '',
        ifShow: '',
        disPrice: '',
        startTime: '',
        endTime: '',
        typeCode: '',
        productPicFile: '',
        auditStatus: '',
        publishStatus: '',
        supplierId: ''
      },
      uploadFileVisible: true,
      dialogImageUrl: '',
      dialogVisible: false,
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
      },
      productTypeList: [{
        typeId: '',
        typeCode: '',
        typeName: ''
      }]
    }
  },
  activated () {
    this.getProductTypeList()
  },
  methods: {
    // 获取产品分类
    getProductTypeList () {
      this.$http({
        url: this.$http.adornUrl(`/product/type/getType`),
        method: 'get',
        params: this.$http.adornParams()
      }).then(({data}) => {
        this.productTypeList = data
      })
    },
    // 返回列表页面
    returnList () {
      this.$parent.isShowUpdateDetail = false
      this.$parent.getDataList()
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
    // 照片上传
    picUpload (productId) {
      return new Promise((resolve, reject) => {
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            this.$http({
              url: this.$http.adornUrl(`/product/picinfo/receive`),
              method: 'post',
              data: this.$http.myFormData({
                'id': productId,
                'file': this.dataForm.productPicFile
              }, true, 'x-www-form-urlencoded')
            }).then(({data}) => {
              if (data && data.code === 0) {
                // this.$message({
                //   message: '上传照片成功',
                //   type: 'success',
                //   duration: 1500
                // })
              } else {
                this.$message.error(data.msg)
              }
            }).catch(({data}) => {
              console.log(data)
              this.$message.error('图片上传失败')
            })
          }
        })
      })
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(`/product/info/update`),
            method: 'post',
            data: this.$http.adornData({
              'productId': this.dataForm.productId,
              'productName': this.dataForm.productName,
              'supplierId': this.dataForm.supplierId,
              'price': this.dataForm.price,
              'freeNum': this.dataForm.freeNum,
              'supplyNum': this.dataForm.supplyNum,
              'perWeight': this.dataForm.perWeight,
              'description': this.dataForm.description,
              'place': this.dataForm.place,
              'breed': this.dataForm.breed,
              'isSpecial': this.dataForm.isSpecial ? 1 : 0,
              'ifShow': this.dataForm.ifShow ? 1 : 0,
              'disPrice': this.dataForm.disPrice,
              'startTime': this.dataForm.startTime,
              'endTime': this.dataForm.endTime,
              'typeCode': this.dataForm.typeCode,
              'publishStatus': this.dataForm.publishStatus ? 1 : 0,
              'auditStatus': this.dataForm.auditStatus
            })
          }).then(({data}) => {
            if (data && data.code === 0) {
              if (data.id) {
                this.picUpload(data.id)
              } else {
                this.picUpload(this.dataForm.productId)
              }
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.$parent.isShowUpdateDetail = false
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
  },
  created () {
    eventBus.$on('send', (val) => {
      this.dataForm = val
    })
  }
}
</script>

<style scoped>

</style>
