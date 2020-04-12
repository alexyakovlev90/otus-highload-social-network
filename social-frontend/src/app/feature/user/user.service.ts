import {HttpClient} from "@angular/common/http";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {ListResponse, ObjectResponse} from "../../shared/response.model";
import {UserInfoItem, UserUpdateItem} from "./user.model";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(protected httpClient: HttpClient) {
  }

  private static apiUrl(): string {
    return "/api/users";
  }

  getById(id: number): Observable<ObjectResponse<UserInfoItem>> {
    return this.httpClient.get<ObjectResponse<UserInfoItem>>(`${UserService.apiUrl()}/${id}`);
  }

  getAll(): Observable<ListResponse<UserInfoItem>> {
    return this.httpClient.get<ListResponse<UserInfoItem>>(`${UserService.apiUrl()}`);
  }

  save(user: UserUpdateItem): Observable<ObjectResponse<UserInfoItem>> {
    if (user.id) {
      return this.httpClient.put<ObjectResponse<UserInfoItem>>(`${UserService.apiUrl()}/${user.id}`, user);
    } else {
      return this.httpClient.post<ObjectResponse<UserInfoItem>>(UserService.apiUrl(), user);
    }
  }

  remove(id: number): Observable<ObjectResponse<any>> {
    return this.httpClient.delete<ObjectResponse<any>>(`${UserService.apiUrl()}/${id}`);
  }
}
