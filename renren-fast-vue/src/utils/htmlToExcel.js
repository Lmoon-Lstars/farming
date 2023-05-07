import FileSaver from 'file-saver'
import * as XLSX from 'xlsx'

const htmlToExcel = {
  getExcel (dom, title = '默认标题') {
    let excelTitle = title
    let wb = XLSX.utils.table_to_book(document.querySelector(dom))
    // 获取二进制字符串作为输出
    let wbout = XLSX.write(wb, {bookType: 'xlsx', bookSST: true, type: 'array'})
    try {
      FileSaver.saveAs(
        new Blob([wbout], {type: 'application/octet-stream'}),
        excelTitle + '.xlsx'
      )
    } catch (e) {
      if (typeof console !== 'undefined') console.log(e, wbout)
    }
  }
}

export default htmlToExcel
