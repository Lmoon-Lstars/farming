<template>
  <el-dialog
    :title="!dataForm.productId ? '新增' : '修改'"
    :close-on-click-modal="false"
    :visible.sync="visible">
    <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
             label-width="200px">

      <el-form-item label="商品名称" prop="productName">
        <el-input v-model="dataForm.productName" placeholder="商品名称"></el-input>
      </el-form-item>
      <el-form-item label="销售价格" prop="price">
        <el-input v-model="dataForm.price" placeholder="商品销售价格"></el-input>
      </el-form-item>
      <el-form-item label="免费供应份数" prop="freeNum">
        <el-input v-model="dataForm.freeNum" placeholder="免费供应份数"></el-input>
      </el-form-item>
      <el-form-item label="正常供应份数" prop="supplyNum">
        <el-input v-model="dataForm.supplyNum" placeholder="正常供应份数"></el-input>
      </el-form-item>
      <el-form-item label="每份重量（克）" prop="perWeight">
        <el-input v-model="dataForm.perWeight" placeholder="每份重量（克）"></el-input>
      </el-form-item>
      <el-form-item label="产地" prop="place">
        <el-input v-model="dataForm.place" placeholder="产地"></el-input>
      </el-form-item>
      <el-form-item label="品种" prop="breed">
        <el-input v-model="dataForm.breed" placeholder="品种"></el-input>
      </el-form-item>
      <el-form-item label="产品类型" prop="typeCode">
        <el-select v-model="dataForm.typeCode" placeholder="请选择产品类型">
          <el-option
            v-for="item in typeCodeOption"
            :key="item.id"
            :label="item.op"
            :value="item.id">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="上下架" prop="publishStatus">
        <el-switch v-model="dataForm.publishStatus"></el-switch>
      </el-form-item>
      <el-form-item label="审核通过" prop="auditStatus">
        <el-switch v-model="dataForm.auditStatus"></el-switch>
      </el-form-item>
      <el-form-item label="特产" prop="isSpecial">
        <el-switch v-model="dataForm.isSpecial"></el-switch>
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
            <el-date-picker type="datetime" placeholder="选择日期" v-model="dataForm.endTime" style="width: 100%;"></el-date-picker>
          </el-form-item>
        </el-col>
      </el-form-item>
      <el-form-item label="商品描述" prop="description">
        <el-input type="textarea" v-model="dataForm.description" placeholder="商品描述"></el-input>
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
        productId: 0,
        productCode: '',
        productName: '',
        price: '',
        supplierId: '',
        freeNum: '',
        supplyNum: '',
        perWeight: '',
        description: '',
        publishStatus: '',
        auditStatus: '',
        place: '',
        breed: '',
        isSpecial: '',
        ifShow: '',
        disPrice: '',
        startTime: '',
        endTime: '',
        typeCode: ''
      },
      typeCodeOption: [
        {id: -1, op: '优惠商品'},
        {id: 1, op: '热带果香'},
        {id: 2, op: '异域佳果'},
        {id: 3, op: '鸿渐柑香'},
        {id: 4, op: '柿落梨香'},
        {id: 5, op: '瑶台琼果'},
        {id: 6, op: '穗果莓芸'},
        {id: 7, op: '藤影垂坐'},
        {id: 8, op: '食语琳琅'}
      ],
      dataRule: {
        // productCode: [
        //   {required: true, message: '商品编码不能为空', trigger: 'blur'}
        // ],
        // productName: [
        //   {required: true, message: '商品名称不能为空', trigger: 'blur'}
        // ],
        // price: [
        //   {required: true, message: '商品销售价格不能为空', trigger: 'blur'}
        // ],
        // supplierId: [
        //   {required: true, message: '商品供应商id不能为空', trigger: 'blur'}
        // ],
        // freeNum: [
        //   {required: true, message: '免费供应份数不能为空', trigger: 'blur'}
        // ],
        // supplyNum: [
        //   {required: true, message: '正常供应份数不能为空', trigger: 'blur'}
        // ],
        // perWeight: [
        //   {required: true, message: '每份重量（克）不能为空', trigger: 'blur'}
        // ],
        // description: [
        //   {required: true, message: '商品描述不能为空', trigger: 'blur'}
        // ],
        // publishStatus: [
        //   {required: true, message: '上下架状态:0下架1上架不能为空', trigger: 'blur'}
        // ],
        // auditStatus: [
        //   {required: true, message: '审核状态:0未审核,1已审核不能为空', trigger: 'blur'}
        // ],
        // place: [
        //   {required: true, message: '产地不能为空', trigger: 'blur'}
        // ],
        // breed: [
        //   {required: true, message: '品种不能为空', trigger: 'blur'}
        // ],
        // isSpecial: [
        //   {required: true, message: '是否为特产:0不是，1是不能为空', trigger: 'blur'}
        // ],
        // startTime: [
        //   {required: true, message: '请设置优惠开始时间', trigger: 'blur'}
        // ],
        // ifShow: [
        //   {required: true, message: '请选择是否优惠', trigger: 'blur'}
        // ],
        // endTime: [
        //   {required: true, message: '请设置优惠结束时间', trigger: 'blur'}
        // ],
        // disPrice: [
        //   {required: true, message: '请填写优惠价', trigger: 'blur'}
        // ],
        // typeCode: [
        //   {required: true, message: '请选择产品类型', trigger: 'blur'}
        // ]
      }
    }
  },
  methods: {
    init (id) {
      this.dataForm.productId = id || 0
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
              this.dataForm.publishStatus = data.info.publishStatus
              this.dataForm.auditStatus = data.info.auditStatus
              this.dataForm.place = data.info.place
              this.dataForm.breed = data.info.breed
              this.dataForm.isSpecial = data.info.isSpecial
              this.dataForm.ifShow = data.info.ifShow
              this.dataForm.startTime = isDate(data.info.startTime)
              this.dataForm.endTime = isDate(data.info.endTime)
              this.dataForm.typeCode = data.info.typeCode
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
