import request from '@/utils/request'

// 查询产品管理列表
export function listProduct(query) {
  return request({
    url: '/link/product/list',
    method: 'get',
    params: query
  })
}

// 查询产品管理详细
export function getProduct(id) {
  return request({
    url: '/link/product/' + id,
    method: 'get'
  })
}

// 新增产品管理
export function addProduct(data) {
  return request({
    url: '/link/product',
    method: 'post',
    data: data
  })
}

// 修改产品管理
export function updateProduct(data) {
  return request({
    url: '/link/product',
    method: 'put',
    data: data
  })
}

// 删除产品管理
export function delProduct(id) {
  return request({
    url: '/link/product/' + id,
    method: 'delete'
  })
}

// 快捷生成
// export function generateProductJson(data) {
//   return request({
//     url: '/link/product/generateProductJson',
//     method: 'post',
//     data: data
//   })
// }

/**
 * 初始化数据模型
 * @param productIds 产品ID集合
 * @param initializeOrNot  是否初始化
 */
// export function initializeDataModel(productIds,initializeOrNot) {
//   return request({
//     url: '/link/product/initializeDataModel/' + productIds + initializeOrNot,
//     method: 'get'
//   })
// }
