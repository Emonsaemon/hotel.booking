export class Reservation {
  id: number;
  room: number;
  user: number;
  startDate: Date;
  endDate: Date;
  price: number;

  constructor(id: number,
              room: number,
              user: number,
              startDate: Date,
              endDate: Date,
              price: number) {
    this.id = id;
    this.room = room;
    this.user = user;
    this.startDate = startDate;
    this.endDate = endDate;
    this.price = price;
  }
}
