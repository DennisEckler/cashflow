import { CategoryResponse, CategoryUpdateRequest } from '@generated/openapi';
import { Category } from '@src/app/shared/model/category';
import { responseToIdentifiers } from '@src/app/shared/mapper/identifier.mapper';

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

export function responseToCategory(response: CategoryResponse): Category {
  return {
    id: response.id!,
    label: response.label!,
    type: response.type!,
    identifier: responseToIdentifiers(response.identifier!),
  };
}

export function responseToCategories(response: CategoryResponse[]): Category[] {
  return response.map(responseToCategory);
}
