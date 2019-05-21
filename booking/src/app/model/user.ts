export class User {
  id: number;
  name: string;
  surname: string;
  email: string;
  phoneNumber: string;
  password: string;


  constructor(id: number, name: string, surname: string, email: string, phoneNumber: string, password: string) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.password = password;
  }
}
