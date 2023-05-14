/**
 * 邮箱
 * @param {*} s
 */
export function isEmail (s) {
  return /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(s)
}

/**
 * 手机号码
 * @param {*} s
 */
export function isMobile (s) {
  return /^1[0-9]{10}$/.test(s)
}

/**
 * 电话号码
 * @param {*} s
 */
export function isPhone (s) {
  return /^([0-9]{3,4}-)?[0-9]{7,8}$/.test(s)
}

/**
 * URL地址
 * @param {*} s
 */
export function isURL (s) {
  return /^http[s]?:\/\/.*/.test(s)
}

/**
 * 处理时间
 * @param date
 * @returns {string}
 */
export function isDate (date) {
  if (date == null) return ''
  let dates = new Date(date).toJSON()
  return new Date(+new Date(dates) + 8 * 3600 * 1000).toISOString().replace(/T/g, ' ').replace(/\.[\d]{3}Z/, '')
}

/**
 * 设置table的固定滚筒条
 */
export function setHeight () {
  const offsetTop = window.innerHeight - this.$refs.table.$el.offsetTop - 210
  const offsetBodyHeight = document.querySelector('.el-table__body').offsetHeight
  console.log(offsetTop, offsetBodyHeight)
  if (this.tableList.length && offsetBodyHeight < offsetTop) {
    this.tableHeight = offsetBodyHeight + 75
  } else if (!this.tableList.length) {
    this.tableHeight = null
  } else {
    this.tableHeight = offsetTop
  }
}

export function isNum (s) {
  return /^[0-9]*$/.test(s)
}
