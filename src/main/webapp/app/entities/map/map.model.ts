import { BaseEntity } from './../../shared';

export class Map implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public imageContentType?: string,
        public image?: any,
        public coverContentType?: string,
        public cover?: any,
        public matches?: BaseEntity[],
        public gameId?: number,
    ) {
    }
}
