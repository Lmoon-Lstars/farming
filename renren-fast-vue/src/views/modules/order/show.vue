<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="search()">
      <el-form-item><span>订单号</span></el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.orderId" placeholder="请输入订单号" clearable @clear="getDataList()"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="search()">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      height="550"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="orderId"
        header-align="center"
        align="center"
        label="订单编号">
      </el-table-column>
      <el-table-column
        prop="orderStatus"
        header-align="center"
        align="center"
        label="订单状态">
        <template slot-scope="scope">
          <!-- scope.row 包含表格里当前行的所有数据 -->
          <el-tag :type="scope.row.orderStatus===2 ? 'success'
                        :scope.row.orderStatus===3 ? 'info'
                        : 'danger'">
            {{
              scope.row.orderStatus === 1 ? '未配送'
                : scope.row.orderStatus === 2 ? '已完成'
                  : '已取消'
            }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        header-align="center"
        align="center"
        :formatter="toFormatDate"
        label="订单生成时间">
      </el-table-column>
      <el-table-column
        prop="customerId"
        header-align="center"
        align="center"
        label="顾客ID">
      </el-table-column>
      <el-table-column
        prop="shippingUser"
        header-align="center"
        align="center"
        label="顾客姓名">
      </el-table-column>
      <el-table-column
        prop="phoneNumber"
        header-align="center"
        align="center"
        label="顾客电话号码">
      </el-table-column>
      <el-table-column
        prop="productName"
        header-align="center"
        align="center"
        label="产品名称">
      </el-table-column>
      <el-table-column
        prop="productCnt"
        header-align="center"
        align="center"
        label="产品数量">
      </el-table-column>
      <el-table-column
        prop="weight"
        header-align="center"
        align="center"
        label="产品总重">
      </el-table-column>
      <el-table-column
        prop="productPrice"
        header-align="center"
        align="center"
        label="产品每份价格">
      </el-table-column>
      <el-table-column
        prop="orderMoney"
        header-align="center"
        align="center"
        label="订单金额">
      </el-table-column>
      <el-table-column
        prop="paymentMoney"
        header-align="center"
        align="center"
        label="付款金额">
      </el-table-column>
      <el-table-column
        prop="remark"
        header-align="center"
        align="center"
        label="备注">
      </el-table-column>
      <el-table-column
        prop="province"
        header-align="center"
        align="center"
        label="省份">
      </el-table-column>
      <el-table-column
        prop="city"
        header-align="center"
        align="center"
        label="城市">
      </el-table-column>
      <el-table-column
        prop="district"
        header-align="center"
        align="center"
        label="街区">
      </el-table-column>
      <el-table-column
        prop="address"
        header-align="center"
        align="center"
        label="详细地址">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="修改订单状态">
        <template slot-scope="scope">
          <el-select v-bind:value="scope.row.orderStatus" placeholder="请选择" @change="click">
            <el-option
              v-for="item in options"
              :key="item.id"
              :label="item.op"
              :value="item.id"
              @click.native="changeGateway(item)">
            </el-option>
          </el-select>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  data () {
    return {
      dataForm: {
        orderId: ''
      },
      dataListLoading: false,
      dataList: [],
      options: [
        {id: 1, op: '未配送'},
        {id: 2, op: '已完成'},
        {id: 3, op: '取消'}
      ]
    }
  },
  activated () {
    this.getDataList()
  },
  methods: {
    changeGateway (data) {
      console.log(data)
    },
    click (val) {
        this.$nextTick((val) => {
          console.log('改变了select')
          // console.log(val.target.id)
          console.log(val)
        })
    },
    toFormatDate (row, column, cellValue, index) {
      if (cellValue == null) return
      let dates = new Date(cellValue).toJSON()
      return new Date(+new Date(dates) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '')
    },
    // 获取数据列表
    getDataList () {
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl(`/order/show/list`),
        method: 'get',
        params: this.$http.adornParams()
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dataList = data.list
        } else {
          this.dataList = []
          alert('搜索无结果')
        }
        this.dataListLoading = false
      })
    },
    // 根据订单id搜索
    search () {
      if (this.dataForm.orderId) {
        this.$http({
          url: this.$http.adornUrl(`/order/show/search?orderId=${this.dataForm.orderId}`),
          method: 'get',
          params: this.$http.adornParams()
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.dataList = data.list
          } else {
            this.dataList = []
            alert('搜索无结果')
          }
        })
      }
    }
  }
}
</script>

<style scoped>

</style>
