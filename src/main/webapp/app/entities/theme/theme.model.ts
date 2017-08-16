import { BaseEntity } from './../../shared';

export class Theme implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public active?: boolean,
        public headercolor?: string,
        public fontcolor?: string,
        public linkcolor?: string,
        public backgroundcolor?: string,
        public profileConfiguration?: BaseEntity,
        public messageRoom?: BaseEntity,
    ) {
        this.active = false;
    }
}
