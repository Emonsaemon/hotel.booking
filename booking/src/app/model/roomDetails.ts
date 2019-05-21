export class RoomDetails {
  id: number;
  floor: number;
  roomNumber: number;
  photo: string[];
  pricePerNight: number;
  bed: number;
  description: string;
  
  constructor(id: number, 
			  floor: number, 
			  roomNumber: number, 
			  photo: string[], 
			  pricePerNight: number,
			  bed: number,
			  description: string) {
	  this.id = id;
	  this.floor = floor;
	  this.roomNumber = roomNumber;
	  this.photo = photo;
	  this.pricePerNight = pricePerNight;
	  this.bed = bed;
	  this.description = description;
	}
}