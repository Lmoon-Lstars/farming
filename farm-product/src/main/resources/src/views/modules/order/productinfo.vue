<template>
  <div class="mod-config">
    <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
      <el-form-item>
        <el-input v-model="dataForm.key" placeholder="参数名" clearable></el-input>
      </el-form-item>
      <el-form-item>
        <el-button @click="getDataList()">查询</el-button>
        <el-button v-if="isAuth('order:productinfo:save')" type="primary" @click="addOrUpdateHandle()">新增</el-button>
        <el-button v-if="isAuth('order:productinfo:delete')" type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除</el-button>
      </el-form-item>
    </el-form>
    <el-table
      :data="dataList"
      border
      v-loading="dataListLoading"
      @selection-change="selectionChangeHandle"
      style="width: 100%;">
      <el-table-column
        type="selection"
        header-align="center"
        align="center"
        width="50">
      </el-table-column>
      <el-table-column
        prop="productId"
        header-align="center"
        align="center"
        label="商品ID">
      </el-table-column>
      <el-table-column
        prop="productCode"
        header-align="center"
        align="center"
        label="商品编码">
      </el-table-column>
      <el-table-column
        prop="productName"
        header-align="center"
        align="center"
        label="商品名称">
      </el-table-column>
      <el-table-column
        prop="priceDifference"
        header-align="center"
        align="center"
        label="价格差">
      </el-table-column>
      <el-table-column
        prop="price"
        header-align="center"
        align="center"
        label="商品销售价格">
      </el-table-column>
      <el-table-column
        prop="supplierId"
        header-align="center"
        align="center"
        label="商品供应商id">
      </el-table-column>
      <el-table-column
        prop="freeNum"
        header-align="center"
        align="center"
        label="免费供应份数">
      </el-table-column>
      <el-table-column
        prop="supplyNum"
        header-align="center"
        align="center"
        label="正常供应份数">
      </el-table-column>
      <el-table-column
        prop="totalNum"
        header-align="center"
        align="center"
        label="总供应份数">
      </el-table-column>
      <el-table-column
        prop="perWeight"
        header-align="center"
        align="center"
        label="每份重量（克）">
      </el-table-column>
      <el-table-column
        prop="description"
        header-align="center"
        align="center"
        label="商品描述">
      </el-table-column>
      <el-table-column
        prop="publishStatus"
        header-align="center"
        align="center"
        label="上下架状态:0下架1上架">
      </el-table-column>
      <el-table-column
        prop="auditStatus"
        header-align="center"
        align="center"
        label="审核状态:0未审核,1已审核">
      </el-table-column>
      <el-table-column
        prop="
place"
        header-align="center"
        align="center"
        label="产地">
      </el-table-column>
      <el-table-column
        prop="breed"
        header-align="center"
        align="center"
        label="品种">
      </el-table-column>
      <el-table-column
        prop="isSpecial"
        header-align="center"
        align="center"
        label="是否为特产:0不是，1是">
      </el-table-column>
      <el-table-column
        prop="indate"
        header-align="center"
        align="center"
        label="商品录入时间">
      </el-table-column>
      <el-table-column
        prop="modifiedTime"
        header-align="center"
        align="center"
        label="最后修改时间">
      </el-table-column>
      <el-table-column
        fixed="right"
        header-align="center"
        align="center"
        width="150"
        label="操作">
        <template slot-scope="scope">
          <el-button type="text" size="small" @click="addOrUpdateHandle(scope.row.productId)">修改</el-button>
          <el-button type="text" size="small" @click="deleteHandle(scope.row.productId)">删除</el-button>
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
    <!-- 弹窗, 新增 / 修改 -->
    <add-or-update v-if="addOrUpdateVisible" ref="addOrUpdate" @refreshDataList="getDataList"></add-or-update>
  </div>
</template>

<script>
  import AddOrUpdate from './productinfo-add-or-update'
  export default {
    data () {
      return {
        dataForm: {
          key: ''
        },
        dataList: [],
        pageIndex: 1,
        pageSize: 10,
        totalPage: 0,
        dataListLoading: false,
        dataListSelections: [],
        addOrUpdateVisible: false
      }
    },
    components: {
      AddOrUpdate
    },
    activated () {
      this.getDataList()
    },
    methods: {
      // 获取数据列表
      getDataList () {
        this.dataListLoading = true
        this.$http({
          url: this.$http.adornUrl('/order/productinfo/list'),
          method: 'get',
          params: this.$http.adornParams({
            'page': this.pageIndex,
            'limit': this.pageSize,
            'key': this.dataForm.key
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
      // 新增 / 修改
      addOrUpdateHandle (id) {
        this.addOrUpdateVisible = true
        this.$nextTick(() => {
          this.$refs.addOrUpdate.init(id)
        })
      },
      // 删除
      deleteHandle (id) {
        var ids = id ? [id] : this.dataListSelections.map(item => {
          return item.productId
        })
        this.$confirm(`确定对[id=${ids.join(',')}]进行[${id ? '删除' : '批量删除'}]操作?`, '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$http({
            url: this.$http.adornUrl('/order/productinfo/delete'),
            method: 'post',
            data: this.$http.adornData(ids, false)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.getDataList()
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        })
      }
    }
  }
</script>
