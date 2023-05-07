<template>
  <div>
    <div class="mod-config" v-show="!isShowInfoAdd&&!isShowUpdateDetail">
      <el-form :inline="true" :model="dataForm" @keyup.enter.native="getDataList()">
        <el-form-item><span>商品名称</span></el-form-item>
        <el-form-item>
          <el-input v-model="dataForm.productName" placeholder="请输入商品名称" clearable
                    @clear="resetSearch()"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button @click="getDataList()">查询</el-button>
          <el-button type="primary" @click="addHandle()">新增</el-button>
          <el-button type="danger" @click="deleteHandle()" :disabled="dataListSelections.length <= 0">批量删除
          </el-button>
        </el-form-item>
      </el-form>
      <el-table
        :data="dataList"
        border
        v-loading="dataListLoading"
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
          fixed
          prop="productId"
          header-align="center"
          align="center"
          label="ID">
        </el-table-column>
        <el-table-column
          fixed
          prop="productName"
          header-align="center"
          align="center"
          label="商品名称">
        </el-table-column>
        <el-table-column
          prop="publishStatus"
          :render-header="renderHeader"
          header-align="center"
          align="center"
          label="上下架">
          <template slot-scope="scope">
            <el-button
              plain
              size="small"
              @click="setPublishStatus(scope.row.productId, scope.row.publishStatus)"
              :type="scope.row.publishStatus===0 ? 'info':'success'">
              {{ scope.row.publishStatus === 0 ? '下架' : '上架' }}
            </el-button>
          </template>
        </el-table-column>
        <el-table-column
          prop="price"
          :render-header="renderHeader"
          header-align="center"
          align="center"
          label="销售价格">
        </el-table-column>
        <el-table-column
          prop="ifShow"
          header-align="center"
          align="center"
          label="是否优惠">
          <template slot-scope="scope">
            <el-tag :type="scope.row.ifShow===0 ? 'info':'danger'">
              {{ scope.row.ifShow === 0 ? '否' : '是' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column
          prop="disPrice"
          header-align="center"
          align="center"
          label="优惠价">
        </el-table-column>
        <el-table-column
          prop="startTime"
          :render-header="renderHeader"
          header-align="center"
          align="center"
          :formatter="toFormatDate"
          label="优惠开始时间">
        </el-table-column>
        <el-table-column
          prop="endTime"
          :render-header="renderHeader"
          header-align="center"
          align="center"
          :formatter="toFormatDate"
          label="优惠结束时间">
        </el-table-column>
        <el-table-column
          prop="priceDifference"
          header-align="center"
          align="center"
          label="价格差">
        </el-table-column>
        <el-table-column
          prop="supplierId"
          :render-header="renderHeader"
          header-align="center"
          align="center"
          label="供应商id">
        </el-table-column>
        <el-table-column
          prop="freeNum"
          :render-header="renderHeader"
          header-align="center"
          align="center"
          label="免费供应份数">
        </el-table-column>
        <el-table-column
          prop="supplyNum"
          :render-header="renderHeader"
          header-align="center"
          align="center"
          label="正常供应份数">
        </el-table-column>
        <el-table-column
          prop="totalNum"
          :render-header="renderHeader"
          header-align="center"
          align="center"
          label="总供应份数">
        </el-table-column>
        <el-table-column
          prop="perWeight"
          :render-header="renderHeader"
          header-align="center"
          align="center"
          label="每份重量/克">
        </el-table-column>
        <el-table-column
          prop="description"
          :render-header="renderHeader"
          header-align="center"
          align="center"
          show-overflow-tooltip
          label="商品描述">
        </el-table-column>
        <el-table-column
          prop="typeCode"
          :render-header="renderHeader"
          header-align="center"
          align="center"
          label="商品类型">
          <template slot-scope="scope">
            <el-tag
              style="color: rgba(12,12,12,0.54)"
              :color="scope.row.typeCode===-1 ? 'LightPink'
            :scope.row.typeCode===1 ?'SandyBrown'
            :scope.row.typeCode===2 ?'LightSkyBlue'
            :scope.row.typeCode===3 ?'lightgrey'
            :scope.row.typeCode===4 ?'PaleGreen'
            :scope.row.typeCode===5 ?'NavajoWhite'
            :'plum'">
              {{ getProductType(scope.row.typeCode) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column
          prop="place"
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
          :render-header="renderHeader"
          header-align="center"
          align="center"
          label="是否为特产">
          <template slot-scope="scope">
            <el-tag :type="scope.row.isSpecial===0 ? 'info':'danger'">
              {{ scope.row.isSpecial === 0 ? '否' : '是' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column
          prop="indate"
          :render-header="renderHeader"
          header-align="center"
          align="center"
          :formatter="toFormatDate"
          label="商品录入时间">
        </el-table-column>
        <el-table-column
          prop="modifiedTime"
          :render-header="renderHeader"
          header-align="center"
          align="center"
          :formatter="toFormatDate"
          label="最后修改时间">
        </el-table-column>
        <el-table-column
          prop="auditStatus"
          :render-header="renderHeader"
          header-align="center"
          align="center"
          label="审核状态">
          <template slot-scope="scope">
            <el-tag :type="scope.row.auditStatus===0 ? 'info':'success'">
              {{ scope.row.auditStatus === 0 ? '未审核' : '已审核' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column
          fixed="right"
          header-align="center"
          align="center"
          width="150"
          label="操作">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="getPicUrl(scope.row.productId)">查看图片</el-button>
            <el-button type="text" size="small" @click="updateHandle(scope.row.productId)">快速修改</el-button>
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
      <!-- 弹窗, 快速修改 -->
      <info-update v-if="updateVisible" ref="update" @refreshDataList="getDataList"></info-update>
      <el-dialog title="查看商品图片" :visible.sync="dialogPicVisible">
        <img width="100%" :src="picUrl" alt="">
      </el-dialog>

    </div>

    <!--新增商品，详细修改 -->
    <div class="mod-config" v-show="isShowInfoAdd">
      <info-add ref="add" @refreshDataList="getDataList"></info-add>
    </div>

    <div class="mod-config" v-show="isShowUpdateDetail">
      <info-update-detail ref="updateDetail" @refreshDataList="getDataList"></info-update-detail>
    </div>

  </div>
</template>

<script>
import InfoUpdate from './info-update'
import InfoAdd from './info-add'
import InfoUpdateDetail from './info-update-detail'

export default {
  data () {
    return {
      dataForm: {
        key: '',
        productName: ''
      },
      productTypeList: [{
        typeId: '',
        typeCode: '',
        typeName: ''
      }],
      dataList: [],
      pageIndex: 1,
      pageSize: 10,
      totalPage: 0,
      dataListLoading: false,
      dataListSelections: [],
      isShowInfoAdd: false,
      updateVisible: false,
      isShowUpdateDetail: false,
      picUrl: '',
      dialogPicVisible: false
    }
  },
  components: {
    InfoUpdateDetail,
    InfoAdd,
    InfoUpdate
  },
  activated () {
    this.getDataList()
    this.getProductTypeList()
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
    // 日期格式化
    toFormatDate (row, column, cellValue, index) {
      if (cellValue == null) return
      let dates = new Date(cellValue).toJSON()
      return new Date(+new Date(dates) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '')
    },
    // 上下架商品
    setPublishStatus (id, status) {
      let tip = ''
      if (status === 0) {
        tip = '上架'
        status = 1
      } else {
        tip = '下架'
        status = 0
      }
      this.$confirm(`是否【${tip}】该商品？`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$http({
          url: this.$http.adornUrl('/product/info/upAndDown'),
          method: 'get',
          params: this.$http.adornParams({
            'productId': id,
            'publishStatus': status
          })
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '操作成功',
              type: 'success',
              duration: 500,
              onClose: () => {
                this.getDataList()
              }
            })
          } else {
            this.$message.error(data.msg)
          }
        })
      })
    },
    // 重置搜索输入框
    resetSearch () {
      this.dataForm.productName = ''
      this.getDataList()
    },
    // 获取数据列表
    getDataList () {
      this.dataListLoading = true
      this.$http({
        url: this.$http.adornUrl('/product/info/list'),
        method: 'get',
        params: this.$http.adornParams({
          'page': this.pageIndex,
          'limit': this.pageSize,
          'key': this.dataForm.key,
          'productName': this.dataForm.productName
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
    // 获取产品类型字段
    getProductType (val) {
      if (val === -1) return this.productTypeList[0].typeName
      return this.productTypeList[val].typeName
    },
    // 获取商品图片url
    getPicUrl (val) {
      this.$http({
        url: this.$http.adornUrl(`/product/picinfo/getPic`),
        method: 'get',
        params: this.$http.adornParams(
          {
            'productId': val
          }
        )
      }).then(({data}) => {
        this.picUrl = data
        this.dialogPicVisible = true
      }).catch(error => {
        console.log(error)
        this.$message({
          message: '错误！图片不存在',
          type: 'error',
          center: true
        })
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
    // 新增
    addHandle () {
      this.isShowInfoAdd = true
      this.$nextTick(() => {
        this.$refs.add.initInput()
      })
    },
    // 快速修改
    updateHandle (id) {
      this.updateVisible = true
      this.$nextTick(() => {
        this.$refs.update.init(id)
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
        // 删除信息
        this.$http({
          url: this.$http.adornUrl('/product/info/delete'),
          method: 'post',
          data: this.$http.adornData(ids, false)
        }).then(({data}) => {
          if (data && data.code === 0) {
            this.$message({
              message: '删除商品信息',
              type: 'success',
              duration: 1500
            })
          } else {
            this.$message.error(data.msg)
          }
        })
        // 删除照片
        this.$http({
          url: this.$http.adornUrl('/product/picinfo/delete'),
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
