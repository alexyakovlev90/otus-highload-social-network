import {UserLiteItem} from "../user-page/user.model";

export interface WallPostListItem {
  id?: number;
  fromUser: UserLiteItem;
  toUser: UserLiteItem;
  dateCreated: string;
  text: string;
}

export interface WallPostUpdateItem {
  fromUser: number;
  toUser: number;
  text: string;
}
