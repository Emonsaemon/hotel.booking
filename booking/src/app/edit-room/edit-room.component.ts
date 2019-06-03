import { Component, OnInit } from '@angular/core';
import {RoomDetails} from "../model/roomDetails";
import {RoomService} from "../service/room.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ImageService} from "../service/image.service";

@Component({
  selector: 'app-edit-room',
  templateUrl: './edit-room.component.html',
  styleUrls: ['./edit-room.component.css']
})
export class EditRoomComponent implements OnInit {

  room: RoomDetails = new RoomDetails(0, 0,0,null,
    0,0,'');
  id = 0;

  selectedFiles: FileList;
  currentFilesUpload: File[];
  progress: { percentage: number } = { percentage: 0 };
  imageToDelete: string;
  xmlRequest: XMLHttpRequest;
  storageURL: string;
  uploadedImages: string[] = null;

  constructor(private roomService: RoomService,
              private router: Router,
              private activatedRoute: ActivatedRoute,
              private imageService: ImageService) {
  }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(params => {
      this.id = +params.get('id');
    });
    this.roomService.findById(this.id).subscribe(value => this.room = value);
  }

  onSubmit() {
    this.roomService.updateRoom(this.room).subscribe(
      data => {
        this.uploadMultipleFiles();
      }
    );
  }

  delete() {
      this.imageService.deleteImage(this.room.id).subscribe(() => console.log("images deleted"));
      this.imageToDelete = undefined;
      window.location.href = 'http://localhost:4200/rooms/' + this.room.id;
    }

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
      if (this.selectedFiles == null) {
        window.location.href = 'http://localhost:4200/rooms/' + this.room.id;
        return;
      }
      this.progress.percentage = 0;
      this.fileListToArray();
      console.log(this.currentFilesUpload);

      this.xmlRequest =  new XMLHttpRequest();
      this.xmlRequest.open(
        'PUT',
        "http://localhost:8080/rooms/" + this.room.id + "/image",
        true);

      const setPercentage = this.setPercentage;
      this.xmlRequest.upload.onprogress = function(event) {
        if (event.lengthComputable) {
          setPercentage(Math.round(100 * event.loaded / event.total));
        }
      };

      const getRoomId = this.getRoomId;

      this.xmlRequest.onload = function() {
        if (this.status === 202) {
          console.log('Files completely uploaded');
          window.location.href = 'http://localhost:4200/rooms/' + getRoomId();
        }
      };
      const formdata = new FormData();
      for (let i = 0; i < this.currentFilesUpload.length; i++) {
        formdata.append('file', this.currentFilesUpload[i]);
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

    getRoomId = () => {
      return this.room.id;
    }

    setPercentage = (percentage: number)  => {
      this.progress.percentage = percentage;
    }

}
