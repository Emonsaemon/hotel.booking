export class RoomDetails {
  id: number;
  floor: number;
  number: number;
  photo: string[];
  pricePerNight: number;
  bed: number;
  description: string;
  
  constructor(id: number, 
			  floor: number, 
			  number: number,
			  photo: string[], 
			  pricePerNight: number,
			  bed: number,
			  description: string) {
	  this.id = id;
	  this.floor = floor;
	  this.number = number;
	  this.photo = photo;
	  this.pricePerNight = pricePerNight;
	  this.bed = bed;
	  this.description = description;
	}
}
