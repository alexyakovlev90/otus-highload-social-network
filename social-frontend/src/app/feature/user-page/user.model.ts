export interface UserInfoItem {
  id?: number;
  login: string;
  firstName: string;
  lastName: string;
  age: number;
  sex: boolean;
  interest: string;
  city: string;
  registerDate: Date;
}

export interface UserUpdateItem {
  id?: number;
  login: string;
  password: string;
  firstName: string;
  lastName: string;
  age: number;
  sex: boolean;
  interest: string;
  city: string;
}


