import { BaseEntity } from './../../shared';

export class Message implements BaseEntity {
    constructor(
        public id?: number,
        public content?: any,
        public created?: any,
        public sent?: any,
        public recived?: any,
        public messageRoomId?: number,
    ) {
    }
}
