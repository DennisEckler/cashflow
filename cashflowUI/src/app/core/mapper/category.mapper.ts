import {
  CategoryResponse,
  CategoryUpdateRequest,
} from 'generated-sources/openapi';

export function catResToCatUpReq(catRes: CategoryResponse) {
  var catCreateReq: CategoryUpdateRequest = {
    id: catRes.id!,
    label: catRes.label!,
    type: catRes.type!,
    userID: catRes.userID!,
    identifier: catRes.identifier,
  };
  return catCreateReq;
}
