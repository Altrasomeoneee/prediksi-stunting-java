import joblib
import argparse
import warnings
from sklearn.exceptions import InconsistentVersionWarning

# Matikan warning dari sklearn
warnings.filterwarnings("ignore", message="X does not have valid feature names")

# Matikan peringatan versi
warnings.filterwarnings("ignore", category=InconsistentVersionWarning)

def single_prediction(model,gender,umur, tinggi, berat):
    if gender.lower() == 'laki-laki':
        gender = 0
    elif gender.lower() == 'perempuan':
        gender = 1
    data = [gender,umur,tinggi,berat]
    hasil = model.predict([data])
    if hasil == 0:
        return "Normal"
    elif hasil == 1:
        return "Sangat Stunting"
    elif hasil == 2:
        return "Stunting"
    elif hasil == 3:
        return "Tinggi"

if __name__ == "__main__":
    parser = argparse.ArgumentParser(description="Predict Stunting Status")
    parser.add_argument('--gender',type=str,required=True, help="gender (laki-laki atau perempuan")
    parser.add_argument('--umur', type=float, required=True, help="Umur (bulan)")
    parser.add_argument('--tinggi', type=float, required=True, help="Tinggi Badan (cm)")
    parser.add_argument('--berat', type=float, required=True, help="Berat Badan (kg)")

    args = parser.parse_args()

    model = joblib.load('src/main/java/com/TIA/stunting_predict/stunting_app/rf_model_stunting.pkl')
    gender = args.gender
    umur = args.umur
    berat = args.berat
    tinggi = args.tinggi

    result = single_prediction(model,gender,umur,tinggi,berat)
    print(result)
