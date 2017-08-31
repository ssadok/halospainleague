import { BaseEntity } from './../../shared';

const enum PlayerState {
    'NOT_PLAYING',
    'IN_TEAM',
    'FREE_AGENT',
    'NOT_SET'
}

const enum Gender {
    'MALE',
    'FEMALE',
    'OTHER',
    'NOT_SPECIFIED'
}

export class Player implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public surname?: string,
        public phone?: string,
        public description?: any,
        public created?: any,
        public profilePhotoContentType?: string,
        public profilePhoto?: any,
        public profileCoverContentType?: string,
        public profileCover?: any,
        public state?: PlayerState,
        public instagram?: string,
        public twitter?: string,
        public youtube?: string,
        public facebook?: string,
        public gender?: Gender,
        public score?: number,
        public address?: string,
        public timeZone?: string,
        public userId?: number,
        public profileConfigurationId?: number,
        public socialAccounts?: BaseEntity[],
        public countryId?: number,
        public notifications?: BaseEntity[],
        public achievements?: BaseEntity[],
        public teams?: BaseEntity[],
        public messageRooms?: BaseEntity[],
    ) {
    }
}
