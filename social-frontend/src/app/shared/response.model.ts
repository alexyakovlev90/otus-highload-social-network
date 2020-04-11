export interface BaseResponse<T> {
  success: boolean;
  result: string;
  msg: string;
  data: T | T[];
}

export interface ObjectResponse<T> extends BaseResponse<T> {
  data: T;
}

export interface ListResponse<T> extends BaseResponse<T> {
  data: T[];
}


export interface PagedListResponse<T> extends ListResponse<T> {
  total:number;
  pageNumber:number;
}
