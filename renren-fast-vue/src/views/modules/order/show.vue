<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()" style="display: inline-block">
      <el-form-item><span style="font-size: 13px">订单号</span></el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.orderId" placeholder="请输入订单号" clearable @clear="getDataList()"
                  size="small"></el-input>
      </el-form-item>
      <el-form-item><span style="font-size: 13px">顾客姓名</span></el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.shipperName" placeholder="请输入顾客姓名" clearable
                  @clear="getDataList()" size="small"></el-input>
      </el-form-item>
      <el-form-item><span style="font-size: 13px">产品名称</span></el-form-item>
      <el-form-item>
        <el-input v-model="dataForm.productName" placeholder="请输入产品名称" clearable
                  @clear="getDataList()" size="small"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="searchWithoutOrderStatus()" size="small">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button @click="resetSearch()" size="small">重置</el-button>
      </el-form-item>
      <el-form-item>
        <el-button @click="searchByOrderStatus()" type="primary" size="small">获取未配送订单</el-button>
      </el-form-item>
      <el-form-item>
        <el-button @click="showTable()" :disabled="dataListSelections.length <= 0" type="primary" size="small">导出
        </el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      @selection-change="selectionChangeHandle"
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
          <el-select v-bind:value="scope.row.orderStatus"
                     @change="changeOrderStatus($event,scope.row.orderId)"
                     size="small">
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
    <el-pagination
      @size-change="sizeChangeHandle"
      @current-change="currentChangeHandle"
      :current-page="pageIndex"
      :page-sizes="[10, 20, 50, 100]"
      :page-size="pageSize"
      :total="totalPage"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>

    <el-dialog
      title="表格预览"
      :visible.sync="tableDialogVisible"
      width="70%">
      <el-table
        id="selectTable"
        :data="tableData"
        style="width: 100%">
        <el-table-column
          prop="orderId"
          label="订单编号"
          width="100">
        </el-table-column>
        <el-table-column
          prop="orderStatus"
          label="订单状态"
          width="80">
        </el-table-column>
        <el-table-column
          prop="createTime"
          label="订单生成时间"
          :formatter="toFormatDate"
          width="180">
        </el-table-column>
        <el-table-column
          prop="customerId"
          label="顾客ID"
          width="80">
        </el-table-column>
        <el-table-column
          prop="shippingUser"
          label="顾客姓名"
          width="100">
        </el-table-column>
        <el-table-column
          prop="phoneNumber"
          label="顾客电话号码"
          width="140">
        </el-table-column>
        <el-table-column
          prop="productName"
          label="产品名称"
          width="180">
        </el-table-column>
        <el-table-column
          prop="productCnt"
          label="产品数量"
          width="80">
        </el-table-column>
        <el-table-column
          prop="weight"
          label="产品总重"
          width="80">
        </el-table-column>
        <el-table-column
          prop="productPrice"
          label="产品每份价格"
          width="120">
        </el-table-column>
        <el-table-column
          prop="orderMoney"
          label="订单金额"
          width="80">
        </el-table-column>
        <el-table-column
          prop="paymentMoney"
          label="付款金额"
          width="80">
        </el-table-column>
        <el-table-column
          prop="remark"
          label="备注"
          width="180">
        </el-table-column>
        <el-table-column
          prop="province"
          label="省份"
          width="100">
        </el-table-column>
        <el-table-column
          prop="city"
          label="城市"
          width="100">
        </el-table-column>
        <el-table-column
          prop="district"
          label="街区"
          width="100">
        </el-table-column>
        <el-table-column
          prop="address"
          label="详细地址"
          width="180">
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="tableDialogVisible=false">取消</el-button>
        <el-button type="primary" @click="exportExcel">导出表格</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import htmlToExcel from '../../../utils/htmlToExcel'

export default {
  data () {
    return {
      dataForm: {
        orderId: '',
        shipperName: '',
        productName: '',
        orderStatus: ''
      },
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      addOrUpdateVisible: false,
      options: [
        {id: 1, op: '未配送'},
        {id: 2, op: '已完成'},
        {id: 3, op: '取消'}
      ],
      tableData: [],
      tableDialogVisible: false
    }
  },
  activated () {
    this.getDataList()
  },
  methods: {
    // 表头撑开
    renderHeader (h, {column, $index}) {
      let span = document.createElement('span')
      // 设置表头名称
      span.innerText = column.label
      // 临时插入 document
      document.body.appendChild(span)
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
        params: this.$http.adornParams({
          'page': this.pageIndex,
          'limit': this.pageSize,
          'key': this.dataForm.key,
          'orderId': this.dataForm.orderId,
          'shipperName': this.dataForm.shipperName,
          'productName': this.dataForm.productName,
          'orderStatus': this.dataForm.orderStatus
        })
      }).then(({data}) => {
        if (data && data.code === 0) {
          this.dataList = data.page.list
          this.totalPage = data.page.totalCount
        } else {
          this.dataList = []
          this.totalPage = 0
        }
        this.dataListLoading = false
      })
    },
    // 每页数
    sizeChangeHandle (val) {
      this.pageSize = val
      this.pageIndex = 1
      this.getDataList()
    },
    // 当前页
    currentChangeHandle (val) {
      this.pageIndex = val
      this.getDataList()
    },
    // 多选
    selectionChangeHandle (val) {
      this.dataListSelections = val
    },
    // 重置搜索输入框
    resetSearch () {
      this.dataForm.orderId = ''
      this.dataForm.shipperName = ''
      this.dataForm.productName = ''
      this.dataForm.orderStatus = ''
      this.getDataList()
    },
    // 根据订单id搜索未配送订单
    searchByOrderStatus () {
      this.dataForm.orderStatus = 1
      this.getDataList()
    },
    // 根据订单id搜索
    searchWithoutOrderStatus () {
      this.dataForm.orderStatus = ''
      this.getDataList()
    },
    // 导出excel表格
    showTable (id) {
      console.log(this.dataListSelections)
      this.tableDialogVisible = true
      // this.$msgbox.confirm('该操作将导出选中的数据，是否继续', '提示', {
      //   confirmButtonText: '确定',
      //   cancelButtonText: '取消',
      //   type: 'warning'
      // })
      this.tableData = this.dataListSelections
      // htmlToExcel.getExcel(this.dataListSelections, 'excel')
    },
    exportExcel () {
      this.$msgbox.confirm('确认导出表格', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        htmlToExcel.getExcel('#selectTable', 'excel')
        this.tableDialogVisible = false
      })
    }
  }
}
</script>

<style scoped>

</style>
