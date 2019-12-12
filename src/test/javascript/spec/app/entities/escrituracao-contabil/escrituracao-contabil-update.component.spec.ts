import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { MandaTestModule } from '../../../test.module';
import { EscrituracaoContabilUpdateComponent } from 'app/entities/escrituracao-contabil/escrituracao-contabil-update.component';
import { EscrituracaoContabilService } from 'app/entities/escrituracao-contabil/escrituracao-contabil.service';
import { EscrituracaoContabil } from 'app/shared/model/escrituracao-contabil.model';

describe('Component Tests', () => {
  describe('EscrituracaoContabil Management Update Component', () => {
    let comp: EscrituracaoContabilUpdateComponent;
    let fixture: ComponentFixture<EscrituracaoContabilUpdateComponent>;
    let service: EscrituracaoContabilService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [MandaTestModule],
        declarations: [EscrituracaoContabilUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EscrituracaoContabilUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EscrituracaoContabilUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EscrituracaoContabilService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EscrituracaoContabil(123);
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
        const entity = new EscrituracaoContabil();
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
