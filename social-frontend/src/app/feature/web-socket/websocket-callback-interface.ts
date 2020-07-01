export interface WebSocketCallBack<T> {
  onDataReceived(response: T);
}

export interface ConnectionCallBack {
  successConnection();
}
