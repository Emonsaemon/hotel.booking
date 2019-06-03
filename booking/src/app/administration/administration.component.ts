import { Component, OnInit } from '@angular/core';
import {RoomDetails} from "../model/roomDetails";
import {RoomService} from "../service/room.service";
import {HttpEventType, HttpResponse} from "@angular/common/http";
import {ImageService} from "../service/image.service";

@Component({
  selector: 'app-administration',
  templateUrl: './administration.component.html',
  styleUrls: ['./administration.component.css']
})
export class AdministrationComponent implements OnInit {

  room: RoomDetails = new RoomDetails(0, 0,0,null,
    0,0,'');

  selectedFiles: FileList;
  currentFilesUpload: File[];
  progress: { percentage: number } = { percentage: 0 };
  imageToDelete: string;
  xmlRequest: XMLHttpRequest;
  storageURL: string;
  uploadedImages: string[] = null;

  constructor(private roomService: RoomService,
              private uploadService: ImageService) { }

  ngOnInit() {
  }

  onSubmit() {
    this.roomService.createRoom(this.room).subscribe(data => {
      this.room = data;
      console.log(this.room);
      this.uploadMultipleFiles();
    });
  }
  //
  // delete(url: string) {
  //   this.imageToDelete=url;
  //   this.uploadService.deleteImage(this.imageToDelete).subscribe(() => console.log("image deleted"));
  //   this.imageToDelete = undefined;
  // }

  selectFile(event) {
    const file = event.target.files.item(0);
    if (file.type.match('image.*')) {
      this.selectedFiles = event.target.files;
    } else {
      alert('invalid format!');
    }
  }

  selectMultipleFiles(event) {
    for (var i = 0; i< event.target.files.length; i++) {
      let file = event.target.files.item(i);
      if (file.type.match('image.*')) {
        this.selectedFiles = event.target.files;
      } else {
        alert('invalid format!');
      }
    }
  }

  uploadMultipleFiles() {
    console.log(11111111111111111111);
    if (this.selectedFiles == null)
      return;
    this.progress.percentage = 0;
    this.fileListToArray();
    console.log(this.currentFilesUpload);

    this.xmlRequest =  new XMLHttpRequest();
    this.xmlRequest.open(
      'POST',
      "http://localhost:8080/rooms/" + this.room.id + "/images",
      true);

    const setPercentage = this.setPercentage;
    this.xmlRequest.upload.onprogress = function(event) {
      if (event.lengthComputable) {
        setPercentage(Math.round(100 * event.loaded / event.total));
      }
    };

    this.xmlRequest.onload = function() {
      if (this.status === 200) {
        console.log('Files completely uploaded');
      }
    };
    const formdata = new FormData();
    for (let i = 0; i < this.currentFilesUpload.length; i++) {
      formdata.append('files', this.currentFilesUpload[i]);
      console.log(this.currentFilesUpload[i]);
    }
    this.xmlRequest.send(formdata);
    this.selectedFiles = undefined;
  }

  fileListToArray() {
    let files = [];
    for (let i = 0; i < this.selectedFiles.length; i++) {
      files.push(this.selectedFiles.item(i));
    }
    this.currentFilesUpload = files;
  }

  setPercentage = (percentage: number)  => {
    this.progress.percentage = percentage;
  }

}
