<template>
  <div>
    <el-dialog
      :title="!dataForm.id ? '新增' : '修改'"
      :close-on-click-modal="false"
      :visible.sync="visible">
      <el-form :model="dataForm" :rules="dataRule" ref="dataForm" @keyup.enter.native="dataFormSubmit()"
               label-width="110px">
        <el-form-item label="所属用户ID" prop="userInfoId">
          <el-row>
            <el-col :span="18">
              <el-input v-model="dataForm.userInfoId" placeholder="请填写折扣券所属用户ID"></el-input>
            </el-col>
            <el-col :span="5" style="float:right;">
              <el-button @click="searchUser">查找用户</el-button>
            </el-col>
          </el-row>
        </el-form-item>
        <el-form-item label="折扣券名称" prop="cuponName">
          <el-input v-model="dataForm.cuponName" placeholder="请填写折扣券名字"></el-input>
        </el-form-item>
        <el-form-item label="优惠金额" prop="cuponAmount">
          <el-input v-model="dataForm.cuponAmount" placeholder="请填写优惠券优惠金额"></el-input>
        </el-form-item>
        <el-form-item label="使用门槛金额" prop="minPoint">
          <el-input v-model="dataForm.minPoint" placeholder="请填写使用门槛金额"></el-input>
        </el-form-item>
        <el-form-item label="活动时间">
          <el-col :span="11">
            <el-form-item prop="startTime">
              <el-date-picker type="datetime" placeholder="请选择折扣券开始日期" v-model="dataForm.startTime"
                              style="width: 100%;"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="2" align="middle">-</el-col>
          <el-col :span="11">
            <el-form-item prop="endTime">
              <el-date-picker type="datetime" placeholder="请选择折扣券结束日期" v-model="dataForm.endTime"
                              style="width: 100%;"></el-date-picker>
            </el-form-item>
          </el-col>
        </el-form-item>
        <el-form-item label="折扣券状态" prop="cuponStatu">
          <el-select v-model="dataForm.cuponStatu" placeholder="请选择折扣券状态">
            <el-option
              v-for="item in cuponStatuList"
              :key="item.typeId"
              :label="item.typeName"
              :value="item.typeId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="使用规则" prop="cuponRules">
          <el-input type="textarea" v-model="dataForm.cuponRules" placeholder="请填写折扣券使用规则"></el-input>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
    </el-dialog>

    <el-dialog title="查找用户" :visible.sync="searchFormVisible" width="30%">
      <el-form :inline="true" @keyup.enter.native="searchUserByPhone()" :rules="dialogRule">
        <el-form-item label="手机号码">
          <el-input v-model="telephone" placeholder="请输入手机号码"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="searchUserByPhone()">查询</el-button>
        </el-form-item>
      </el-form>
      <p>用户ID： {{ userInfo.userInfoId }}</p> <br>
      <p>用户昵称： {{ userInfo.nickName }}</p> <br>
      <p>手机号码： {{ userInfo.mobilePhone }}</p>
      <div slot="footer" class="dialog-footer">
        <el-button @click="searchFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="writeID">填入ID</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data () {
    return {
      visible: false,
      searchFormVisible: false,
      telephone: '',
      userInfo: {
        userInfoId: '',
        plantPoint: '',
        mobilePhone: '',
        nickName: ''
      },
      dataForm: {
        id: 0,
        cuponName: '',
        minPoint: '',
        cuponAmount: '',
        startTime: '',
        endTime: '',
        userInfoId: '',
        cuponStatu: '',
        cuponType: '',
        cuponRules: ''
      },
      cuponStatuList: [
        {typeId: 0, typeName: '未开始'},
        {typeId: 1, typeName: '过期'},
        {typeId: 2, typeName: '有效'},
        {typeId: 3, typeName: '已使用'}
      ],
      dataRule: {
        cuponName: [
          {required: true, message: '折扣券名称不能为空', trigger: 'blur'}
        ],
        cuponAmount: [
          {required: true, message: '折扣券优惠金额不能为空', trigger: 'blur'}
        ],
        minPoint: [
          {required: true, message: '折扣券门槛金额不能为空', trigger: 'blur'}
        ],
        startTime: [
          {required: true, message: '开始时间不能为空', trigger: 'blur'}
        ],
        endTime: [
          {required: true, message: '结束时间不能为空', trigger: 'blur'}
        ],
        userInfoId: [
          {required: true, message: '用户id不能为空', trigger: 'blur'}
        ],
        cuponStatu: [
          {required: true, message: '请选择折扣券状态', trigger: 'blur'}
        ]
      },
      dialogRule: {
        telephone: [
          {required: true, message: '请填写电话号码', trigger: 'blur'}
        ]
      }
    }
  },
  methods: {
    init (id) {
      this.dataForm.id = id || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
        if (this.dataForm.id) {
          this.$http({
            url: this.$http.adornUrl(`/user/cupon/info/${this.dataForm.id}`),
            method: 'get',
            params: this.$http.adornParams()
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.dataForm.cuponName = data.cupon.cuponName
              this.dataForm.minPoint = data.cupon.minPoint
              this.dataForm.cuponAmount = data.cupon.cuponAmount
              this.dataForm.startTime = data.cupon.startTime
              this.dataForm.endTime = data.cupon.endTime
              this.dataForm.userInfoId = data.cupon.userInfoId
              this.dataForm.cuponStatu = data.cupon.cuponStatu
              this.dataForm.cuponType = data.cupon.cuponType
              this.dataForm.cuponRules = data.cupon.cuponRules
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
            url: this.$http.adornUrl(`/user/cupon/${!this.dataForm.id ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData({
              'id': this.dataForm.id || undefined,
              'cuponName': this.dataForm.cuponName,
              'minPoint': this.dataForm.minPoint,
              'cuponAmount': this.dataForm.cuponAmount,
              'startTime': this.dataForm.startTime,
              'endTime': this.dataForm.endTime,
              'userInfoId': this.dataForm.userInfoId,
              'cuponStatu': this.dataForm.cuponStatu,
              'cuponType': -1,
              'cuponRules': this.dataForm.cuponRules
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
        } else {
          this.$message({
            message: '表单填写不规范,请重新填写',
            type: 'error',
            duration: 1500
          })
        }
      })
    },
    searchUser () {
      this.telephone = ''
      this.searchFormVisible = true
      this.userInfo = ''
    },
    // 根据手机号查找用户信息 并回填到输入框中
    searchUserByPhone () {
      this.$http({
        url: this.$http.adornUrl(`/user/info/findUser`),
        method: 'get',
        params: this.$http.adornParams({
          'phoneNumber': this.telephone
        })
      }).then(({data}) => {
        if (data.user && data.code === 0) {
          this.userInfo = data.user
        } else {
          this.$message({
            message: '用户不存在',
            type: 'error',
            duration: 1500
          })
        }
      })
    },
    // 填写id
    writeID () {
      this.dataForm.userInfoId = this.userInfo.userInfoId
      this.searchFormVisible = false
    }
  }
}
</script>
