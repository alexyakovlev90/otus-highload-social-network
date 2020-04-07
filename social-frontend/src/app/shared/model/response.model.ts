export interface BaseResponse<T> {
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

