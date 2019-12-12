import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { RetencaoFonteUpdateComponent } from 'app/entities/retencao-fonte/retencao-fonte-update.component';
import { RetencaoFonteService } from 'app/entities/retencao-fonte/retencao-fonte.service';
import { RetencaoFonte } from 'app/shared/model/retencao-fonte.model';

describe('Component Tests', () => {
  describe('RetencaoFonte Management Update Component', () => {
    let comp: RetencaoFonteUpdateComponent;
    let fixture: ComponentFixture<RetencaoFonteUpdateComponent>;
    let service: RetencaoFonteService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [RetencaoFonteUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(RetencaoFonteUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(RetencaoFonteUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(RetencaoFonteService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new RetencaoFonte(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new RetencaoFonte();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
