// не надо передавать примитивы
import {BaseResponse} from "../../shared/response.model";

export interface TopicListener<T> {
  messageReceived(data: T);

  errorReceived(response: BaseResponse<T>);
}
