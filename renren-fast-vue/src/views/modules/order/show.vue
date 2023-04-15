<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="search()">
      <el-form-item><span>订单号</span></el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.orderId" placeholder="请输入订单号" clearable @clear="search()"></el-input>
      </el-form-item>
      <el-form-item><span>顾客姓名</span></el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.shipperName" placeholder="请输入顾客姓名" clearable @clear="search()"></el-input>
      </el-form-item>
      <el-form-item><span>产品名称</span></el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.productName" placeholder="请输入产品名称" clearable @clear="search()"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="search()">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button @click="resetSearch()">重置</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
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
        :render-header="renderHeader"
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
        :render-header="renderHeader"
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
        :render-header="renderHeader"
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
          <el-select v-bind:value="scope.row.orderStatus" placeholder="请选择"
                     @change="changeOrderStatus($event,scope.row.orderId)">
            <el-option
              v-for="item in options"
              :key="item.id"
              :label="item.op"
              :value="item.id">
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
        orderId: '',
        shipperName: '',
        productName: ''
      },
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
    renderHeader (h, {column, $index}) {
      let span = document.createElement('span')
      // 设置表头名称
      span.innerText = column.label
      // 临时插入 document
      document.body.appendChild(span)
      console.log(span.getBoundingClientRect().width)
      // 重点：获取 span 最小宽度，设置当前列，注意这里加了 20，字段较多时还是有挤压，且渲染后的 div 内左右 padding 都是 10，所以 +20 。（可能还有边距/边框等值，需要根据实际情况加上）
      column.minWidth = span.getBoundingClientRect().width + 50
      // 移除 document 中临时的 span
      document.body.removeChild(span)
      return h('span', column.label)
    },
    // 修改订单状态
    changeOrderStatus (val, id) {
      this.$http({
        url: this.$http.adornUrl(`/order/master/finish`),
        method: 'post',
        data: this.$http.myFormData({
          'orderId': id,
          'orderStatus': val
        }, true, 'x-www-form-urlencoded')
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.$message({
            message: '操作成功',
            type: 'success',
            duration: 1500
          })
          this.$nextTick(() => {
            this.getDataList()
          })
        }
      })
    },
    // 格式化日期
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
        }
      })
    },
    // 重置搜索输入框
    resetSearch () {
      this.dataForm.orderId = ''
      this.dataForm.shipperName = ''
      this.dataForm.productName = ''
      this.getDataList()
    },
    // 根据订单id搜索
    search () {
      this.$http({
        url: this.$http.adornUrl(`/order/show/search`),
        method: 'get',
        params: this.$http.adornParams({
          'orderId': this.dataForm.orderId,
          'shipperName': this.dataForm.shipperName,
          'productName': this.dataForm.productName
        })
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dataList = data.list
        } else {
          this.dataList = []
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
