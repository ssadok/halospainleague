import { BaseEntity } from './../../shared';

export class ProfileConfiguration implements BaseEntity {
    constructor(
        public id?: number,
        public sendNews?: boolean,
        public privateMessages?: boolean,
        public teamInvites?: boolean,
        public showDescription?: boolean,
        public showScore?: boolean,
        public showSocial?: boolean,
        public showAge?: boolean,
        public showGender?: boolean,
        public active?: boolean,
        public timeZone?: string,
        public lastLogin?: any,
        public firstRun?: boolean,
        public showTutorial?: boolean,
        public player?: BaseEntity,
        public themes?: BaseEntity[],
    ) {
        this.sendNews = false;
        this.privateMessages = false;
        this.teamInvites = false;
        this.showDescription = false;
        this.showScore = false;
        this.showSocial = false;
        this.showAge = false;
        this.showGender = false;
        this.active = false;
        this.firstRun = false;
        this.showTutorial = false;
    }
}
